package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.enums.DefenseGroupType;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.mapper.DefenseGroupMapper;
import cn.zttek.thesis.modules.mapper.DefenseTaskMapper;
import cn.zttek.thesis.modules.mapper.RoleMapper;
import cn.zttek.thesis.modules.mapper.UserMapper;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.DefenseTask;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mankind on 2017/8/20.
 */
@Service
public class DefenseGroupService extends BaseService<DefenseGroup>{

    @Autowired
    private DefenseGroupMapper defenseGroupMapper;

    @Autowired
    private DefenseTaskMapper defenseTaskMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<DefenseGroup> listAll(Integer page, Integer rows, Long taskid) throws Exception{
        log.info("===查询答辩任务下所有答辩小组列表===");
        PageHelper.startPage(page, rows);
        List<DefenseGroup> list =defenseGroupMapper.selectByTask(taskid);
        PageInfo<DefenseGroup> pageInfo = new PageInfo<DefenseGroup>(list);
        return pageInfo;
    }
    public List<DefenseGroup>  listAll( Long taskid) throws Exception{
        log.info("===查询答辩任务下所有答辩小组列表===");
        return defenseGroupMapper.selectByTask(taskid);
    }

    public void deleteById(Long id) throws  Exception{
        log.info("===删除某个答辩小组[" + id + "]===");
        DefenseGroup defenseGroup=this.queryById(id);
        defenseGroupMapper.deleteByPrimaryKey(id);
    }

    public void deleteStudentByJSON(String jsondata,DefenseGroup defenseGroup) throws Exception{
        log.info("===删除答辩小组下的参与学生====");
        if(defenseGroup.getStudents()!=null){
            List<ThesisDefenseStudent> stulist= JsonUtils.jsonToList(defenseGroup.getStudents(),ThesisDefenseStudent.class);
            List<ThesisDefenseStudent> deleteStuList=JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
            log.info("stulist数据:"+stulist);
            log.info("deleteStuList数据:"+deleteStuList);
            log.info("删除前长度："+stulist.size());
            stulist.removeAll(deleteStuList);
            log.info("删除后长度："+stulist.size());
            defenseGroup.setStudents(JsonUtils.objectToJson(stulist));
            defenseGroupMapper.updateByPrimaryKey(defenseGroup);
        }
    }
    public void deleteTeacherByJSON(String jsondata,DefenseGroup defenseGroup) throws Exception{
        log.info("===删除答辩小组下的参与教师====");
        if(defenseGroup.getTeachers()!=null){
            List<ThesisDefenseTeacher> teacherlist=JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);
            List<ThesisDefenseTeacher> deleteTeacherList=JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
            teacherlist.removeAll(deleteTeacherList);
            defenseGroup.setTeachers(JsonUtils.objectToJson(teacherlist));
            defenseGroupMapper.updateByPrimaryKey(defenseGroup);
        }
    }
    public void addStudentByJSON(String jsondata,DefenseGroup defenseGroup) throws Exception{
        log.info("===添加答辩小组下的参与学生====");
        List<ThesisDefenseStudent> stulist=new ArrayList<ThesisDefenseStudent>();
        if(defenseGroup.getStudents()!=null) {
            stulist = JsonUtils.jsonToList(defenseGroup.getStudents(), ThesisDefenseStudent.class);
        }
        List<ThesisDefenseStudent> addStuList=JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
        stulist.addAll(addStuList);
        defenseGroup.setStudents(JsonUtils.objectToJson(stulist));
        defenseGroupMapper.updateByPrimaryKey(defenseGroup);
    }
    public void addTeacherByJSON(String jsondata,DefenseGroup defenseGroup) throws Exception{
        log.info("===添加答辩任务下的参与教师====");
        List<ThesisDefenseTeacher> teacherlist=new ArrayList<ThesisDefenseTeacher>();
        if(defenseGroup.getTeachers()!=null){
            teacherlist=JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);
        }
        List<ThesisDefenseTeacher> addTeacherList=JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
        teacherlist.addAll(addTeacherList);
        defenseGroup.setTeachers(JsonUtils.objectToJson(teacherlist));
        defenseGroupMapper.updateByPrimaryKey(defenseGroup);
    }
    public PageInfo<ThesisDefenseStudent> listStudent( Long taskid, String stuno, DefenseStatus defenseStatus,DefenseGroup defenseGroup) throws Exception {
        log.info("===查询答辩任务下所有未分配的学生===");
        if(taskid==null)taskid=defenseGroup.getTaskid();
        DefenseTask task=defenseTaskMapper.selectByPrimaryKey(taskid);
        List<ThesisDefenseStudent> studentlist=new ArrayList<ThesisDefenseStudent>();
        if(task.getStudents()!=null){
            //获取答辩任务下所有学生列表
            studentlist=JsonUtils.jsonToList(task.getStudents(),ThesisDefenseStudent.class);
            //过滤出对应答辩类型的学生
            if(defenseStatus!=null){
                int length=studentlist.size();
                for(int i=length-1;i>=0;i--){
                    if(!studentlist.get(i).getDefenseStatus().equals(defenseStatus)){
                        studentlist.remove(i);
                    }
                }
            }
            //获取已经分配的所有学生
            List<DefenseGroup> groups=defenseGroupMapper.selectByTask(taskid);
            for(DefenseGroup group:groups){
                if(group.getStudents()!=null){
                    List<ThesisDefenseStudent> groupStudentList=JsonUtils.jsonToList(group.getStudents(),ThesisDefenseStudent.class);
                    studentlist.removeAll(groupStudentList);
                }
            }
            Collections.sort(studentlist);
        }
        PageInfo<ThesisDefenseStudent> pageInfo=new PageInfo<ThesisDefenseStudent>(studentlist);
        return pageInfo;
    }

    public PageInfo<ThesisDefenseTeacher> listTeacher(Long taskid,String leaderJSON,String secretaryJSON,TitleLevel titleLevel,DefenseGroup defenseGroup) throws Exception {
        log.info("===查询答辩任务下所有未分配的教师===");
        if(taskid==null)taskid=defenseGroup.getTaskid();
        DefenseTask task=defenseTaskMapper.selectByPrimaryKey(taskid);
        List<ThesisDefenseTeacher> teacherlist=new ArrayList<ThesisDefenseTeacher>();
        if(task.getTeachers()!=null){
            //获取答辩任务下所有教师列表
            teacherlist=JsonUtils.jsonToList(task.getTeachers(),ThesisDefenseTeacher.class);
            //过滤不符合等级条件的教师
            teacherlist=levelFilter(titleLevel,teacherlist);
            //过滤已经分配的教师
            teacherlist=existTeacherFilter(teacherlist);
            if(defenseGroup.getId()!=null&&defenseGroup.getId()>0){
                teacherlist.add(defenseGroupMapper.selectTeacherByUserId(defenseGroup.getLeaderid()));
                teacherlist.add(defenseGroupMapper.selectTeacherByUserId(defenseGroup.getSecretaryid()));
            }
            //过滤前面被选择的答辩组长
            if(StringUtil.isNotEmpty(leaderJSON)){
                teacherlist.remove(JsonUtils.jsonToPojo(leaderJSON,ThesisDefenseTeacher.class));
            }
            //过滤前面被选择的答辩秘书
            if(StringUtil.isNotEmpty(secretaryJSON)){
                teacherlist.remove((JsonUtils.jsonToPojo(secretaryJSON,ThesisDefenseTeacher.class)));
            }
            Collections.sort(teacherlist);
        }
        PageInfo<ThesisDefenseTeacher> pageInfo=new PageInfo<ThesisDefenseTeacher>(teacherlist);
        return pageInfo;
    }
    public List<ThesisDefenseTeacher> levelFilter(TitleLevel titleLevel,List<ThesisDefenseTeacher> teacherlist) throws  Exception{
        //过滤不符合等级条件的教师
        if(titleLevel!=null){
            int length=teacherlist.size();
            for(int i=length-1;i>=0;i--){
                if(!teacherlist.get(i).getTitleLevel().equals(titleLevel)){
                    teacherlist.remove(i);
                }
            }
        }
        return teacherlist;
    }
    public String getTeacherJSON(Long teacherid) throws Exception{
        log.info("转化后的json"+JsonUtils.objectToJson(defenseGroupMapper.selectTeacherByUserId(teacherid)));
        return JsonUtils.objectToJson(defenseGroupMapper.selectTeacherByUserId(teacherid));
    }
    public ThesisDefenseTeacher getTeacherById(Long teacherid) throws Exception{
        return defenseGroupMapper.selectTeacherByUserId(teacherid);
    }
    public List<ThesisDefenseTeacher> existTeacherFilter(List<ThesisDefenseTeacher> teacherlist) throws  Exception{
        List<DefenseGroup> groups=defenseGroupMapper.selectAll();
        for(DefenseGroup group:groups){
            //去除答辩参加教师
            if(group.getTeachers()!=null){
                List<ThesisDefenseTeacher> groupTeacherList=JsonUtils.jsonToList(group.getTeachers(),ThesisDefenseTeacher.class);
                teacherlist.removeAll(groupTeacherList);
            }
            //去除答辩秘书和答辩组长
            if(group.getSecretaryid()!=null){
                ThesisDefenseTeacher leader=defenseGroupMapper.selectTeacherByUserId(group.getLeaderid());
                teacherlist.remove(leader);
            }
            if(group.getSecretaryid()!=null){
                ThesisDefenseTeacher secretary=defenseGroupMapper.selectTeacherByUserId(group.getSecretaryid());
                teacherlist.remove(secretary);
            }
        }
        return teacherlist;
    }
    public Integer getGroupno(Long taskid) throws Exception{
        List<DefenseGroup> list=defenseGroupMapper.selectByTask(taskid);
        DefenseTask defenseTask=defenseTaskMapper.selectByPrimaryKey(taskid);
        int[] table=new int[defenseTask.getNums()+1];
        for(int i=0;i<list.size();i++){
            table[list.get(i).getGroupno()]=1;
        }
        int index=1;
        for(int i=defenseTask.getNums();i>0;i--){
            if(table[i]!=1)index=i;
        }
        return index;
    }

    public List<DefenseGroup> autoGroup(List<DefenseGroup> groups,Integer groupnum,Integer groupTeacherNum, DefenseGroupType grouptype,List<ThesisDefenseTeacher> leaders, List<ThesisDefenseTeacher> secretarys, List<ThesisDefenseTeacher> teachers, DefenseTask defenseTask) throws  Exception{
        log.info("===开始自动分组===");
        log.info("===删除对应类型的已经存在的分组===");
        for(DefenseGroup group:groups){
            if(group.getGrouptype().equals(grouptype)){
                defenseGroupMapper.deleteByPrimaryKey(group.getId());
            }
        }
        log.info("===自动分组答辩任务初始化===");
        List<DefenseGroup> newgroups=new ArrayList<DefenseGroup>();
        for(int i=0;i<groupnum;i++){
            newgroups.add(new DefenseGroup());
        }
        log.info("===按照工号顺序分配答辩秘书===");
        log.info(""+secretarys);
        Collections.sort(secretarys);
        log.info(""+secretarys);
        for(int i=0;i<groupnum;i++){
            ThesisDefenseTeacher secretary=secretarys.get(i);
            newgroups.get(i).setSecretaryid(secretary.getTeacherid());
            teachers.remove(secretary);
            leaders.remove(secretary);
        }
        log.info("===按照工号顺序分配答辩组长===");
        Collections.sort(leaders);
        for(int i=0;i<groupnum;i++){
            ThesisDefenseTeacher leader=leaders.get(i);
            newgroups.get(i).setLeaderid(leader.getTeacherid());
            teachers.remove(leader);
        }
        log.info("===按工号顺序分配参加教师===");
        Collections.sort(teachers);
        int teachersSize=teachers.size();
        //循环遍历每一个组按工号顺序分配参加教师
        for(int i=0;i<groupnum;i++){
            List<ThesisDefenseTeacher> teacher=new ArrayList<ThesisDefenseTeacher>();
            for(int j=0;j<groupTeacherNum;j++){
                int index=i*groupTeacherNum+j;
                if(index<teachersSize){
                    teacher.add(teachers.get(index));
                }
            }
            newgroups.get(i).setTeachers(JsonUtils.objectToJson(teacher));
        }
        log.info("===按学号顺序分配参加学生===");
        List<ThesisDefenseStudent> students=new ArrayList<ThesisDefenseStudent>();
        if(defenseTask.getStudents()!=null){
            students=JsonUtils.jsonToList(defenseTask.getStudents(),ThesisDefenseStudent.class);
        }
        //保留对应类型的学生
        for(int i=students.size()-1;i>=0;i--){
            ThesisDefenseStudent student=students.get(i);
            if(!student.getDefenseStatus().getId().equals(grouptype.getId())){
                students.remove(student);
            }
        }
        //获取每组学生人数
        Integer groupStuNums=null;
        int studentsSize=students.size();
        if(students.size()%groupnum==0){
            groupStuNums=studentsSize/groupnum;
        }else{
            groupStuNums=studentsSize/groupnum+1;
        }
        Collections.sort(students);
        //循环遍历每一个组按学号顺序分配学生
        for(int i=0;i<groupnum;i++){
            List<ThesisDefenseStudent> stu=new ArrayList<ThesisDefenseStudent>();
            for(int j=0;j<groupStuNums;j++){
                int index=i*groupStuNums+j;
                if(index<studentsSize){
                    stu.add(students.get(index));
                }
            }
            newgroups.get(i).setStudents(JsonUtils.objectToJson(stu));
        }
        log.info("===自动分组-将分配好的所有小组插入数据库===");
        for(int i=0;i<groupnum;i++){
            DefenseGroup group=newgroups.get(i);
            group.setGroupno(getGroupno(defenseTask.getId()));
            group.setProjectid(ThesisParam.getCurrentProj().getId());
            group.setGrouptype(grouptype);
            group.setDefensetime(defenseTask.getDefensetime());
            group.setTaskid(defenseTask.getId());
            defenseGroupMapper.insert(newgroups.get(i));
        }
        return newgroups;
    }
}
