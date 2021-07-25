package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.common.utils.TimeUtils;
import cn.zttek.thesis.modules.enums.DefenseGroupType;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.excel.MyExcelView;
import cn.zttek.thesis.modules.expand.GuideStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.DefenseGroupService;
import cn.zttek.thesis.modules.service.DefenseTaskService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Mankind on 2017/8/20.
 */
@Controller
@RequestMapping("/console/thesis/defense/group")
public class ThesisDefenseGroupController extends BaseController{

    @Autowired
    private DefenseTaskService defenseTaskService;

    @Autowired
    private DefenseGroupService defenseGroupService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private ThesisService thesisService;

    @ModelAttribute("defenseGroup")
    public DefenseGroup get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return defenseGroupService.queryById(id);
        } else {
            return new DefenseGroup();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model,@ModelAttribute("taskid") Long taskid) throws Exception{
        //model.addAttribute("taskid",taskid);
        return "console/thesis/defense/group/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Model model, Integer page, Integer rows,Long taskid) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<DefenseGroup> pageInfo =defenseGroupService.listAll(page, rows,taskid);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
    @RequestMapping(value = "/edit", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute DefenseGroup defenseGroup, Model model) throws Exception {
        model.addAttribute("leaderJSON",defenseGroupService.getTeacherJSON(defenseGroup.getLeaderid()));
        model.addAttribute("secretaryJSON",defenseGroupService.getTeacherJSON(defenseGroup.getSecretaryid()));
        return "console/thesis/defense/group/edit";
    }
    @RequestMapping(value = "/edit-{type}", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String editType(@PathVariable String type, Model model) throws Exception {
        model.addAttribute("titles",TitleLevel.values());
        return "console/thesis/defense/group/edit-edit";
    }
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String add(Model model,Long taskid) throws Exception{
        model.addAttribute("defenseTask",defenseTaskService.queryById(taskid));
        model.addAttribute("titles", TitleLevel.values());
            model.addAttribute("defenseStatuses", DefenseStatus.values());
        return "console/thesis/defense/group/add";
    }
    @RequestMapping(value = "/autogroup", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String autogroup(Model model,Long taskid) throws Exception{
        model.addAttribute("defenseTask",defenseTaskService.queryById(taskid));
        model.addAttribute("titles", TitleLevel.values());
        model.addAttribute("grouptypes", DefenseGroupType.values());
        return "console/thesis/defense/group/autogroup";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult delete(String ids){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = "答辩小组删除成功";
                for (Long id : idsArry) {
                    defenseGroupService.deleteById(id);
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除答辩小组时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的答辩小组！");
        }
        return result;
    }

    @RequestMapping(value = "/student-list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult studentlist(Integer page,Integer rows,Long taskid,String stuno,DefenseStatus defenseStatus,@ModelAttribute DefenseGroup defenseGroup) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<ThesisDefenseStudent> pageInfo = defenseGroupService.listStudent(taskid,stuno,defenseStatus,defenseGroup);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
    @RequestMapping(value = "/teacher-list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult teacherlist(@ModelAttribute DefenseGroup defenseGroup, Integer page,Integer rows,Long taskid,String leaderJSON,String secretaryJSON,TitleLevel titleLevel,Long leaderid,Long secretaryid) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<ThesisDefenseTeacher> pageInfo = defenseGroupService.listTeacher(taskid,leaderJSON,secretaryJSON,titleLevel,defenseGroup);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
    @RequestMapping(value = "/wizard", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult wizard(Integer step_number, HttpServletRequest request) throws Exception{
        EUResult result = null;
        if(step_number == 1){
            String grouptype = request.getParameter("grouptype");
            if(StringUtils.isNotEmpty(grouptype)){
                //TODO 判断组数是否符合条件
            }else{
                result = EUResult.build(EUResult.FAIL, "请输入答辩名称！");
            }
        }
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute DefenseGroup defenseGroup, HttpServletRequest request) {
        EUResult result = new EUResult();
        try {
            //如果是编辑
            if (defenseGroup.getId() != null && defenseGroup.getId() > 0) {
                defenseGroupService.update(defenseGroup);
            } else {
                //如果是添加
                defenseGroup.setProjectid(ThesisParam.getCurrentProj().getId());
                //获取当前可用的最小编号
                log.info("当前可用的最小编号为:"+defenseGroupService.getGroupno(defenseGroup.getTaskid()));
                defenseGroup.setGroupno(defenseGroupService.getGroupno(defenseGroup.getTaskid()));
                defenseGroupService.insert(defenseGroup);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("答辩小组信息保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{type}-showlist.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult showlist(Integer page, Integer rows,@PathVariable String type,@ModelAttribute DefenseGroup defenseGroup) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        if("student".equals(type)){
            if(defenseGroup.getStudents()!=null){
                List<ThesisDefenseStudent> students= JsonUtils.jsonToList(defenseGroup.getStudents(),ThesisDefenseStudent.class);
                Collections.sort(students);
                PageInfo<ThesisDefenseStudent> pageInfo=new PageInfo<ThesisDefenseStudent>(students);
                result.setTotal(pageInfo.getTotal());
                result.setRows(pageInfo.getList());
            }
        }else if("teacher".equals(type)){
            if(defenseGroup.getTeachers()!=null){
                List<ThesisDefenseTeacher> teachers=JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);
                Collections.sort(teachers);
                PageInfo<ThesisDefenseTeacher> pageInfo=new PageInfo<ThesisDefenseTeacher>(teachers);
                result.setTotal(pageInfo.getTotal());
                result.setRows(pageInfo.getList());
            }
        }
        return result;
    }

    @RequestMapping(value = "/delete-{type}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult deleteStudentOrTeacher(String jsondata,@PathVariable String type,@ModelAttribute DefenseGroup defenseGroup){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(jsondata)) {
            try {
                result.setStatus(EUResult.OK);
                if("student".equals(type)){
                    defenseGroupService.deleteStudentByJSON(jsondata,defenseGroup);
                }else if("teacher".equals(type)){
                    defenseGroupService.deleteTeacherByJSON(jsondata,defenseGroup);
                }
                String msg = "删除答辩任务参加"+("student".equals(type)?"学生":"教师")+"成功";
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除答辩任务参加"+("student".equals(type)?"学生":"教师")+"时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的"+("student".equals(type)?"学生":"教师")+"！");
        }
        return result;
    }
    @RequestMapping(value = "/edit-add-{type}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public String addStudentOrTeacher(Model model,@PathVariable String type,@ModelAttribute DefenseGroup defenseGroup) throws Exception{
        if(!"student".equals(type)){
            model.addAttribute("titles", TitleLevel.values());
        }
        return "console/thesis/defense/group/edit-add";
    }
    @RequestMapping(value = "/edit-add-{type}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult add(String jsondata,@PathVariable String type,@ModelAttribute DefenseGroup defenseGroup){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(jsondata)){
            try{
                result.setStatus(EUResult.OK);
                if("student".equals(type)){
                    defenseGroupService.addStudentByJSON(jsondata,defenseGroup);
                }else if("teacher".equals(type)){
                    defenseGroupService.addTeacherByJSON(jsondata,defenseGroup);
                }
                String msg = "添加答辩任务参加"+("student".equals(type)?"学生":"教师")+"成功";
                result.setMsg(msg);
            }catch(Exception e){
                result.setStatus(EUResult.FAIL);
                result.setMsg("添加答辩任务参加"+("student".equals(type)?"学生":"教师")+"时发生异常！" + e.getMessage());
            }
        }else{
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要添加的"+("student".equals(type)?"学生":"教师")+"！");

        }
        return result;
    }
    @RequestMapping(value ="/autogroup", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult autogroup(Long taskid,TitleLevel leaderLevel,TitleLevel secretaryLevel,DefenseGroupType grouptype,HttpServletRequest request) throws Exception{
        EUResult result = new EUResult();
        if(leaderLevel!=null&&secretaryLevel!=null&&grouptype!=null){
            DefenseTask defenseTask=defenseTaskService.queryById(taskid);
            if(defenseTask.getTeachers()!=null){
                List<ThesisDefenseTeacher> teachers=JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
                //每组参加教师人数
                Integer groupTeacherNum=teachers.size()/defenseTask.getNums()-2;
                //去除另外一个类型已有答辩小组的教师,答辩秘书和答辩组长
                List<DefenseGroup> groups=defenseGroupService.listAll(taskid);
                for(DefenseGroup group:groups){
                    if(!group.getGrouptype().equals(grouptype)){
                        if(group.getTeachers()!=null){
                            teachers.removeAll(JsonUtils.jsonToList(group.getTeachers(),ThesisDefenseTeacher.class));
                        }
                        if(group.getSecretaryid()!=null){
                            teachers.remove(defenseGroupService.getTeacherById(group.getSecretaryid()));
                        }
                        if(group.getLeaderid()!=null){
                            teachers.remove(defenseGroupService.getTeacherById(group.getLeaderid()));
                        }
                    }
                }
                List<ThesisDefenseTeacher> leaders=defenseTaskService.getTeachersByTitle(teachers,leaderLevel);
                List<ThesisDefenseTeacher> secretarys=defenseTaskService.getTeachersByTitle(teachers,secretaryLevel);
                //获取组数
                Integer groupnum=null;
                if(grouptype.equals(DefenseGroupType.NORMAL)){
                    groupnum=defenseTask.getNums()-defenseTask.getExcelnums();
                }else if(grouptype.equals(DefenseGroupType.EXCEL)){
                    groupnum=defenseTask.getExcelnums();
                }
                // 检查答辩秘书组长人数是否符合分配要求
                if(leaders.size()>=groupnum){
                    if(secretaryLevel!=leaderLevel&&secretarys.size()>=groupnum
                            ||secretaryLevel==leaderLevel&&secretarys.size()>=2*groupnum){
                        //检查答辩教师是否足够分配
                        if(teachers.size()-2*groupnum>=groupTeacherNum){
                            //开始自动分组
                            try{
                                List<DefenseGroup> newgroups=defenseGroupService.autoGroup(groups,groupnum,groupTeacherNum,grouptype,leaders,secretarys,teachers,defenseTask);
                                result.setStatus(EUResult.OK);
                                result.setMsg("已经成功为您分配答辩小组共"+newgroups.size()+"组!");
                            }catch (Exception e){
                                result.setStatus(EUResult.FAIL);
                                result.setMsg("自动分组时发生异常！"+e.getMessage());

                            }
                        }else{
                            result.setStatus(EUResult.FAIL);
                            result.setMsg("参加教师不足以分配！");
                        }
                    }else{
                        result.setStatus(EUResult.FAIL);
                        result.setMsg("符合等级条件的答辩秘书只有"+secretarys.size()+"名！");
                    }
                }else{
                    result.setStatus(EUResult.FAIL);
                    result.setMsg("符合等级条件的答辩组长只有"+leaders.size()+"名！");
                }
            }

        }else{
            result.setStatus(EUResult.FAIL);
            result.setMsg("存在未填写的字段，请填写完毕后再提交！");
        }
        return result;
    }










    //************************************合并分割线，下面是黄锦荣所写***************************************************

    @RequestMapping(value = "/teacherview", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String teacherView(Model model) throws Exception {
        Project currentProj=ThesisParam.getCurrentProj();
        User teacher=ThesisParam.getCurrentUser();
        HashMap groups=defenseGroupService.getByTeacher(currentProj.getId(),teacher.getId());
        model.addAttribute("user",teacher);
        model.addAttribute("teacher",(ArrayList<DefenseGroup>)groups.get("teacher"));
        model.addAttribute("students",(ArrayList<GuideStudent>)groups.get("students"));
        ArrayList<DefenseGroup> test=(ArrayList<DefenseGroup>)groups.get("teacher");
        return "/console/thesis/defense/group/teacherview";
    }

    @RequestMapping(value = "/view", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute DefenseGroup defenseGroup, Model model) throws Exception{

        List<ThesisDefenseStudent> students=JsonUtils.jsonToList(defenseGroup.getStudents(),ThesisDefenseStudent.class);
        List<ThesisDefenseTeacher> teachers= JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);
        Project project=ThesisParam.getCurrentProj();
        for (ThesisDefenseStudent student:students){
            Thesis thesis=thesisService.getStudentThesis(project.getId(),student.getStudentid());
            student.setTopic(thesis.getTopic());
            student.setThesisid(thesis.getId());
        }
        model.addAttribute("students",students);
        model.addAttribute("teachers",teachers);
        model.addAttribute("defeseGroup",defenseGroup);
        return "console/thesis/defense/group/view";
    }




    @RequestMapping(value = "/exportGroup", method = RequestMethod.GET)
    public ModelAndView exportGroup(Long groupid) throws Exception{
        DefenseGroup results = defenseGroupService.queryById(groupid);
        return new ModelAndView(getGroupExcelView(results));
    }


    @RequestMapping(value = "/exportAllGroup",method = RequestMethod.GET)
    public ModelAndView exportAllGroup(@RequestParam Long id) throws Exception{
       List<DefenseGroup> groups= defenseGroupService.listAll(id);
       return new ModelAndView(getAllGroupExcelView(groups));
    }



    private MyExcelView getAllGroupExcelView(List<DefenseGroup> defenseGroups){
        log.info("===创建Excel文件并输出===");
        String name = "所有答辩分组";
        String[] titles = {"答辩分组","答辩地点","答辩组成员","专业","学号","学生"};
        List<Object[]> groups=new ArrayList<>();
        for(int i=0;i<defenseGroups.size();i++){
            List<ThesisDefenseStudent> students= JsonUtils.jsonToList(defenseGroups.get(i).getStudents(),ThesisDefenseStudent.class);
            List<ThesisDefenseTeacher> teachers=JsonUtils.jsonToList(defenseGroups.get(i).getTeachers(),ThesisDefenseTeacher.class);
            List<Object[]> list=loadData(teachers,students,defenseGroups.get(i));
            groups.addAll(list);
        }
        return new MyExcelView(name, titles,groups);
    }



    private MyExcelView getGroupExcelView(DefenseGroup results){
        log.info("===创建Excel文件并输出===");
        String name = "答辩分组";
        String[] titles = {"答辩分组","答辩时间","答辩地点","答辩组成员","专业","学号","学生"};
        List<ThesisDefenseStudent> students= JsonUtils.jsonToList(results.getStudents(),ThesisDefenseStudent.class);
        List<ThesisDefenseTeacher> teachers=JsonUtils.jsonToList(results.getTeachers(),ThesisDefenseTeacher.class);
        List<Object[]> list=loadData(teachers,students,results);
        return new MyExcelView(name, titles,list);
    }

    private List<Object[]> loadData(List<ThesisDefenseTeacher> teachers,List<ThesisDefenseStudent> students,DefenseGroup results){
        int size=students.size()>teachers.size()?students.size():teachers.size();
        List<Object[]> list = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            Object[] objects=new Object[10];
            if(i==0){
                objects[0]="第"+results.getGroupno()+"组";
                objects[1]= TimeUtils.timestampToString(results.getDefensetime());
                objects[2]=results.getDefenseroom();
                objects[3]="组长:"+results.getLeaderName();
            }
            else if(i==1){
                objects[3]="秘书:"+results.getSecretaryName();
            }else{
                if(i<teachers.size()){
                    objects[3]="答辩老师:"+teachers.get(i).getUserName();
                }
            }
            if(i<students.size()){
                objects[4]=students.get(i).getClazz();
                objects[5]=students.get(i).getStuno();
                objects[6]=students.get(i).getStuname();
            }
            list.add(objects);
        }  Object[] objects=new Object[10];
        list.add(objects);
        return list;
    }

}
