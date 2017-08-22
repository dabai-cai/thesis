package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.mapper.DefenseGroupMapper;
import cn.zttek.thesis.modules.mapper.DefenseTaskMapper;
import cn.zttek.thesis.modules.model.Advice;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.DefenseTask;
import cn.zttek.thesis.modules.model.Thesis;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Mankind on 2017/8/16.
 */
@Service
public class DefenseTaskService extends BaseService<DefenseTask>{

    @Autowired
    private DefenseTaskMapper defenseTaskMapper;

    @Autowired
    private DefenseGroupMapper defenseGroupMapper;

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
            List<ThesisDefenseStudent> stulist=JsonUtils.jsonToList(defenseTask.getStudents(),ThesisDefenseStudent.class);
            List<ThesisDefenseStudent> deleteStuList=JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
            stulist.removeAll(deleteStuList);
            defenseTask.setStudents(JsonUtils.objectToJson(stulist));
            defenseTaskMapper.updateByPrimaryKey(defenseTask);
        }
    }
    public void deleteTeacherByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===删除答辩任务下的参与教师====");
        if(defenseTask.getTeachers()!=null){
            List<ThesisDefenseTeacher> teacherlist=JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
            List<ThesisDefenseTeacher> deleteTeacherList=JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
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
        List<ThesisDefenseStudent> addStuList=JsonUtils.jsonToList(jsondata,ThesisDefenseStudent.class);
        stulist.addAll(addStuList);
        defenseTask.setStudents(JsonUtils.objectToJson(stulist));
        defenseTaskMapper.updateByPrimaryKey(defenseTask);
    }
    public void addTeacherByJSON(String jsondata,DefenseTask defenseTask) throws Exception{
        log.info("===添加答辩任务下的参与教师====");
        List<ThesisDefenseTeacher> teacherlist=new ArrayList<ThesisDefenseTeacher>();
        if(defenseTask.getTeachers()!=null){
            teacherlist=JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
        }
            List<ThesisDefenseTeacher> addTeacherList=JsonUtils.jsonToList(jsondata,ThesisDefenseTeacher.class);
            teacherlist.addAll(addTeacherList);
            defenseTask.setTeachers(JsonUtils.objectToJson(teacherlist));
            defenseTaskMapper.updateByPrimaryKey(defenseTask);
    }
    public PageInfo<ThesisDefenseStudent> listStudent(Integer page, Integer rows,Long projid,ThesisResult thesisResult) throws Exception {
        log.info("===查询论文工作下所有未分配的学生===");
        List<ThesisDefenseStudent> studentlist=defenseTaskMapper.studentlistByProj(projid,thesisResult.getMajor(),thesisResult.getGrade(),thesisResult.getClazz(),thesisResult.getStuno());
        //List<ThesisDefenseStudent> studentsList=defenseTaskMapper.selectNotAllotedStudent(orgid,thesisResult.getMajor(),thesisResult.getGrade(),thesisResult.getClazz(),thesisResult.getStuno());
        //获取已经分配的所有学生
        List<DefenseTask> tasks=defenseTaskMapper.listByProj(projid);
        for(DefenseTask task:tasks){
            List<ThesisDefenseStudent> groupStudentList=JsonUtils.jsonToList(task.getStudents(),ThesisDefenseStudent.class);
            if(groupStudentList!=null){
                studentlist.removeAll(groupStudentList);
            }
        }
        Collections.sort(studentlist);
        PageInfo<ThesisDefenseStudent> pageInfo=new PageInfo<ThesisDefenseStudent>(studentlist);

        return pageInfo;
    }

    public PageInfo<ThesisDefenseTeacher> listTeacher(Integer page, Integer rows,Long projid,ThesisResult thesisResult) throws Exception {
        log.info("===查询所有未分配的老师===");
        List<ThesisDefenseTeacher> teachersList=defenseTaskMapper.teacherlistByProj(projid,thesisResult.getTitle(),thesisResult.getTeacher());
        List<DefenseTask> tasks=defenseTaskMapper.listByProj(projid);
        for(DefenseTask task:tasks){
            List<ThesisDefenseTeacher> groupTeacherList=JsonUtils.jsonToList(task.getTeachers(),ThesisDefenseTeacher.class);
            if(groupTeacherList!=null){
                teachersList.removeAll(groupTeacherList);
            }
        }
        Collections.sort(teachersList);
        PageInfo<ThesisDefenseTeacher> pageInfo=new PageInfo<ThesisDefenseTeacher>(teachersList);
        return pageInfo;
    }

    public DefenseTask getByName(String name){
        return defenseTaskMapper.selectByName(name);
    }
}
