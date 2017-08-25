package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.mapper.DefenseGroupMapper;
import cn.zttek.thesis.modules.mapper.DefenseTaskMapper;
import cn.zttek.thesis.modules.mapper.ProjectMapper;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.DefenseTask;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mankind on 2017/8/16.
 */
@Service
public class DefenseTaskService extends BaseService<DefenseTask> {

    @Autowired
    private DefenseTaskMapper defenseTaskMapper;

    @Autowired
    private DefenseGroupMapper defenseGroupMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public PageInfo<DefenseTask> listAll(Integer page, Integer rows, Long projid) throws Exception{
        log.info("===查询所有答辩任务列表===");
        PageHelper.startPage(page, rows);
        List<DefenseTask> list = defenseTaskMapper.listByProj(projid);
        //添加答辩任务的总答辩人数以及已答辩人数字段
        for(DefenseTask defenseTask:list ){
            if(defenseTask.getStudents()!=null){
                List<ThesisDefenseStudent> studentList= JsonUtils.jsonToList(defenseTask.getStudents(),ThesisDefenseStudent.class);
                defenseTask.setDefenseNum(studentList.size());
            }
            List<DefenseGroup> defenseGroupList=defenseGroupMapper.selectByTask(defenseTask.getId());
            int allotnum=0;
            //获取答辩任务下所有答辩小组
            for(DefenseGroup group:defenseGroupList){
                if(group.getStudents()!=null) {
                    List<ThesisDefenseStudent> groupStudentList = JsonUtils.jsonToList(group.getStudents(), ThesisDefenseStudent.class);
                    allotnum += groupStudentList.size();
                }
            }
            defenseTask.setAllotNum(allotnum);
        }
        PageInfo<DefenseTask> pageInfo = new PageInfo<DefenseTask>(list);

        return pageInfo;
    }

    public String deleteById(Long id) throws  Exception{
        log.info("===删除某个答辩任务[" + id + "]===");
        String msg = "";
        DefenseTask defenseTask=this.queryById(id);
        defenseTaskMapper.deleteByPrimaryKey(id);
        defenseGroupMapper.deleteByTask(id);
        msg="答辩任务删除成功";
        return msg;
    }
    public void deleteStudentByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===删除答辩任务下的参与学生====");
        if(defenseTask.getStudents()!=null){
            List<ThesisDefenseStudent> stulist= JsonUtils.jsonToList(defenseTask.getStudents(),ThesisDefenseStudent.class);
            List<ThesisDefenseStudent> deleteStuList= JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
            stulist.removeAll(deleteStuList);
            defenseTask.setStudents(JsonUtils.objectToJson(stulist));
            defenseTaskMapper.updateByPrimaryKey(defenseTask);
        }
    }
    public void deleteTeacherByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===删除答辩任务下的参与教师====");
        if(defenseTask.getTeachers()!=null){
            List<ThesisDefenseTeacher> teacherlist= JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
            List<ThesisDefenseTeacher> deleteTeacherList= JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
            teacherlist.removeAll(deleteTeacherList);
            defenseTask.setTeachers(JsonUtils.objectToJson(teacherlist));
            defenseTaskMapper.updateByPrimaryKey(defenseTask);
        }
    }
    public void addStudentByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===添加答辩任务下的参与学生====");
        List<ThesisDefenseStudent> stulist=new ArrayList<ThesisDefenseStudent>();
        if(defenseTask.getStudents()!=null) {
            stulist = JsonUtils.jsonToList(defenseTask.getStudents(), ThesisDefenseStudent.class);
        }
        List<ThesisDefenseStudent> addStuList= JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
        stulist.addAll(addStuList);
        defenseTask.setStudents(JsonUtils.objectToJson(stulist));
        defenseTaskMapper.updateByPrimaryKey(defenseTask);
    }
    public void addTeacherByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===添加答辩任务下的参与教师====");
        List<ThesisDefenseTeacher> teacherlist=new ArrayList<ThesisDefenseTeacher>();
        if(defenseTask.getTeachers()!=null){
            teacherlist= JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
        }
            List<ThesisDefenseTeacher> addTeacherList= JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
            teacherlist.addAll(addTeacherList);
            defenseTask.setTeachers(JsonUtils.objectToJson(teacherlist));
            defenseTaskMapper.updateByPrimaryKey(defenseTask);
    }
    public PageInfo<ThesisDefenseStudent> listStudent(Integer page, Integer rows, Long projid, ThesisResult thesisResult) throws Exception {
        log.info("===查询论文工作下所有未分配的学生===");
        List<ThesisDefenseStudent> studentlist=defenseTaskMapper.studentlistByProj(projid,null,null,null,thesisResult.getStuno());

        //List<ThesisDefenseStudent> studentsList=defenseTaskMapper.selectNotAllotedStudent(orgid,thesisResult.getMajor(),thesisResult.getGrade(),thesisResult.getClazz(),thesisResult.getStuno());
        //获取已经分配的所有学生
        List<DefenseTask> tasks=defenseTaskMapper.listByProj(projid);
        for(DefenseTask task:tasks){
            List<ThesisDefenseStudent> groupStudentList= JsonUtils.jsonToList(task.getStudents(),ThesisDefenseStudent.class);
            if(groupStudentList!=null){
                studentlist.removeAll(groupStudentList);
            }
        }
        Collections.sort(studentlist);
        PageInfo<ThesisDefenseStudent> pageInfo=new PageInfo<ThesisDefenseStudent>(studentlist);

        return pageInfo;
    }
    public PageInfo<ThesisDefenseTeacher> listTeacher(Integer page, Integer rows, Long projid, TitleLevel titleLevel, String account, Timestamp defensetime) throws Exception {
        log.info("===查询所有未分配的老师===");
        Project project= ThesisParam.getCurrentProj();
        List<Project> projects=projectMapper.listByOrgAndYear(project.getOrgid(),project.getYear());
        //遍历所有活跃论文工作
        List<ThesisDefenseTeacher> teacherList=new ArrayList<ThesisDefenseTeacher>();
        for(Project p:projects){
            //取出每一个论文工作的参与教师，去重后添加列表中
            List<ThesisDefenseTeacher> teachers=defenseTaskMapper.teacherlistByProj(p.getId(),titleLevel,account);
            teacherList.removeAll(teachers);
            teacherList.addAll(teachers);
        }
        //List<ThesisDefenseTeacher> teachersList=defenseTaskMapper.teacherlistByProj(projid,titleLevel,account);
        for(Project p:projects){
            //取出每一个论文工作下的所有答辩任务,去除已经参加的教师
            List<DefenseTask> tasks=defenseTaskMapper.listByProj(p.getId());
            for(DefenseTask task:tasks){
                //不同论文工作，只去除答辩时间相同的老师
                if(defensetime.equals(task.getDefensetime())){
                    List<ThesisDefenseTeacher> groupTeacherList= JsonUtils.jsonToList(task.getTeachers(),ThesisDefenseTeacher.class);
                    if(groupTeacherList!=null){
                        teacherList.removeAll(groupTeacherList);
                    }
                }
            }
        }
        Collections.sort(teacherList);
        PageInfo<ThesisDefenseTeacher> pageInfo=new PageInfo<ThesisDefenseTeacher>(teacherList);
        return pageInfo;
    }

    public DefenseTask getByName(String name){
        return defenseTaskMapper.selectByName(name);
    }
}
