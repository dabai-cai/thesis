package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.expand.GuideStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.DefenseGroupService;
import cn.zttek.thesis.modules.service.DefenseTaskService;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Mankind on 2017/8/20.
 */
@Controller
@RequestMapping("/console/thesis/defense/group")
public class ThesisDefenseGroupController extends BaseController {

    @Autowired
    private DefenseTaskService defenseTaskService;

    @Autowired
    private DefenseGroupService defenseGroupService;

    @Autowired
    private TitleService titleService;

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
    public EUDataGridResult list(Model model, Integer page, Integer rows, Long taskid) throws Exception{
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
        model.addAttribute("titles", TitleLevel.values());
        return "console/thesis/defense/group/edit-edit";
    }
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String add(Model model,Long taskid) throws Exception{
        model.addAttribute("defenseTask",defenseTaskService.queryById(taskid));
        model.addAttribute("titles", TitleLevel.values());
            model.addAttribute("defenseStatuses", DefenseStatus.values());
        return "console/thesis/defense/group/add";
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
    public EUDataGridResult studentlist(Integer page, Integer rows, Long taskid, String stuno, DefenseStatus defenseStatus, @ModelAttribute DefenseGroup defenseGroup) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<ThesisDefenseStudent> pageInfo = defenseGroupService.listStudent(taskid,stuno,defenseStatus,defenseGroup);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
    @RequestMapping(value = "/teacher-list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult teacherlist(@ModelAttribute DefenseGroup defenseGroup, Integer page, Integer rows, Long taskid, String leaderJSON, String secretaryJSON, TitleLevel titleLevel, Long leaderid, Long secretaryid) throws Exception{
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
    public EUDataGridResult showlist(Integer page, Integer rows, @PathVariable String type, @ModelAttribute DefenseGroup defenseGroup) throws Exception{
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
                List<ThesisDefenseTeacher> teachers= JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);
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
    public EUResult deleteStudentOrTeacher(String jsondata, @PathVariable String type, @ModelAttribute DefenseGroup defenseGroup){
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
    public EUResult add(String jsondata, @PathVariable String type, @ModelAttribute DefenseGroup defenseGroup){
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









    //************************************合并分割线，下面是黄锦荣所写***************************************************

    @RequestMapping(value = "/teacherview", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String teacherView(Model model) throws Exception {
        Project currentProj=ThesisParam.getCurrentProj();
        User teacher=ThesisParam.getCurrentUser();
        HashMap groups=defenseGroupService.getByTeacher(currentProj.getId(),teacher.getId());
        model.addAttribute("teacher",(ArrayList<DefenseGroup>)groups.get("teacher"));
        model.addAttribute("students",(ArrayList<GuideStudent>)groups.get("students"));
        return "/console/thesis/defense/group/teacherview";
    }

    @RequestMapping(value = "/view", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute DefenseGroup defenseGroup, Model model) throws Exception{

        List<ThesisDefenseStudent> students=JsonUtils.jsonToList(defenseGroup.getStudents(),ThesisDefenseStudent.class);
        List<ThesisDefenseTeacher> teachers= JsonUtils.jsonToList(defenseGroup.getTeachers(),ThesisDefenseTeacher.class);

        model.addAttribute("students",students);
        model.addAttribute("teachers",teachers);
        model.addAttribute("defeseGroup",defenseGroup);
        return "console/thesis/defense/group/view";
    }

}
