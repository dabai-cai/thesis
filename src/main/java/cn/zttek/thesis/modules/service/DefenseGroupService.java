package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.mapper.DefenseGroupMapper;
import cn.zttek.thesis.modules.mapper.DefenseTaskMapper;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.DefenseTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PageInfo<DefenseGroup> listAll(Integer page, Integer rows, Long taskid) throws Exception{
        log.info("===查询答辩任务下所有答辩小组列表===");
        PageHelper.startPage(page, rows);
        List<DefenseGroup> list =defenseGroupMapper.selectByTask(taskid);
        PageInfo<DefenseGroup> pageInfo = new PageInfo<DefenseGroup>(list);
        return pageInfo;
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
            stulist.removeAll(deleteStuList);
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
    public PageInfo<ThesisDefenseStudent> listStudent(Integer page, Integer rows,Long taskid,String stuno,String grouptype) throws Exception {
        log.info("===查询答辩任务下所有未分配的学生===");
        DefenseTask task=defenseTaskMapper.selectByPrimaryKey(taskid);
        List<ThesisDefenseStudent> studentlist=new ArrayList<ThesisDefenseStudent>();
        if(task.getStudents()!=null){
            //获取答辩任务下所有学生列表
            studentlist=JsonUtils.jsonToList(task.getStudents(),ThesisDefenseStudent.class);
            //过滤出对应答辩类型的学生

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

    public PageInfo<ThesisDefenseTeacher> listTeacher(Integer page, Integer rows,Long taskid,String title,String leaderJSON,String secretaryJSON) throws Exception {
        log.info("===查询答辩任务下所有未分配的教师===");
        DefenseTask task=defenseTaskMapper.selectByPrimaryKey(taskid);
        List<ThesisDefenseTeacher> teacherlist=new ArrayList<ThesisDefenseTeacher>();
        if(task.getTeachers()!=null){
            //获取答辩任务下所有教师列表
            teacherlist=JsonUtils.jsonToList(task.getTeachers(),ThesisDefenseTeacher.class);
            //获取已经分配的所有教师
            List<DefenseGroup> groups=defenseGroupMapper.selectAll();
            for(DefenseGroup group:groups){
                //去除答辩教师
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
            if(StringUtil.isNotEmpty(leaderJSON)){
                teacherlist.remove(JsonUtils.jsonToPojo(leaderJSON,ThesisDefenseTeacher.class));
            }
            if(StringUtil.isNotEmpty(secretaryJSON)){
                teacherlist.remove((JsonUtils.jsonToPojo(secretaryJSON,ThesisDefenseTeacher.class)));
            }
            Collections.sort(teacherlist);
        }
        PageInfo<ThesisDefenseTeacher> pageInfo=new PageInfo<ThesisDefenseTeacher>(teacherlist);
        return pageInfo;
    }
}
