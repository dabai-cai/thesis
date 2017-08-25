package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.model.DefenseTask;
import cn.zttek.thesis.modules.service.DefenseTaskService;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mankind on 2017/8/16.
 */
@Controller
@RequestMapping("/console/thesis/defense/task")
public class ThesisDefenseTaskController extends BaseController {

    @Autowired
    private DefenseTaskService defenseTaskService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private UserService userService;

    @ModelAttribute("defenseTask")
    public DefenseTask get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return defenseTaskService.queryById(id);
        } else {
            return new DefenseTask();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        return "console/thesis/defense/task/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Model model, Integer page, Integer rows) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<DefenseTask> pageInfo =defenseTaskService.listAll(page, rows, ThesisParam.getCurrentProj().getId());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = "/edit", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute DefenseTask defenseTask, Model model) throws Exception {
        return "console/thesis/defense/task/edit";
    }
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String add(Model model) throws Exception{
        model.addAttribute("titles", TitleLevel.values());
        return "console/thesis/defense/task/add";
    }
    @RequestMapping(value = "/view", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(Model model) throws Exception{
        return "console/thesis/defense/task/view";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult delete(String ids){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = "";
                for (Long id : idsArry) {
                    msg = msg + defenseTaskService.deleteById(id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除答辩任务时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的答辩任务！");
        }
        return result;
    }

    @RequestMapping(value = "/{type}-list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult addlist(Integer page, Integer rows, @PathVariable String type, ThesisResult thesisResult, TitleLevel titleLevel, Timestamp defensetime) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        if("student".equals(type)){
            PageInfo<ThesisDefenseStudent> pageInfo = defenseTaskService.listStudent(page,rows, ThesisParam.getCurrentProj().getId(),thesisResult);
            result.setTotal(pageInfo.getTotal());
            result.setRows(pageInfo.getList());
        }else if("teacher".equals(type)){
            PageInfo<ThesisDefenseTeacher> pageInfo = defenseTaskService.listTeacher(page,rows, ThesisParam.getCurrentProj().getId(),titleLevel,thesisResult.getTeacher(),defensetime);
            result.setTotal(pageInfo.getTotal());
            result.setRows(pageInfo.getList());
        }
        return result;
    }
    @RequestMapping(value = "/wizard", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult wizard(Integer step_number, HttpServletRequest request) throws Exception{
        EUResult result = null;
        if(step_number == 1){
            String name = request.getParameter("name");
            if(StringUtils.isNotEmpty(name)){
                DefenseTask defenseTask = defenseTaskService.getByName(name);
                if(defenseTask != null){
                    result = EUResult.build(EUResult.FAIL, "系统中已存在该答辩任务！");
                }
                else
                    result= EUResult.build(EUResult.OK,"");
            }else{
                result = EUResult.build(EUResult.FAIL, "请输入答辩名称！");
            }
        }
        return result;
    }
    @RequestMapping(value = {"/add", "/edit"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute DefenseTask defenseTask, HttpServletRequest request) {
        EUResult result = new EUResult();
        try {
            //如果是编辑
            if (defenseTask.getId() != null && defenseTask.getId() > 0) {
                defenseTaskService.update(defenseTask);
            } else {
                //如果是添加
                defenseTask.setProjectid(ThesisParam.getCurrentProj().getId());
                defenseTaskService.insert(defenseTask);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("答辩任务信息保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{type}-showlist.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult showlist(Integer page, Integer rows, @PathVariable String type, @ModelAttribute DefenseTask defenseTask) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        if("student".equals(type)){
            if(defenseTask.getStudents()!=null){
                List<ThesisDefenseStudent> students= JsonUtils.jsonToList(defenseTask.getStudents(),ThesisDefenseStudent.class);
                Collections.sort(students);
                PageInfo<ThesisDefenseStudent> pageInfo=new PageInfo<ThesisDefenseStudent>(students);
                result.setTotal(pageInfo.getTotal());
                result.setRows(pageInfo.getList());
            }
        }else if("teacher".equals(type)){
            if(defenseTask.getTeachers()!=null){
                List<ThesisDefenseTeacher> teachers= JsonUtils.jsonToList(defenseTask.getTeachers(),ThesisDefenseTeacher.class);
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
    public EUResult deleteStudentOrTeacher(String jsondata, @PathVariable String type, @ModelAttribute DefenseTask defenseTask){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(jsondata)) {
            try {
                result.setStatus(EUResult.OK);
                 if("student".equals(type)){
                    defenseTaskService.deleteStudentByJSON(jsondata,defenseTask);
                }else if("teacher".equals(type)){
                    defenseTaskService.deleteTeacherByJSON(jsondata,defenseTask);
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
    public String addStudentOrTeacher(Model model,@PathVariable String type) throws Exception{
        if("teacher".equals(type)){
            model.addAttribute("titles", TitleLevel.values());
        }
        return "console/thesis/defense/task/edit-add";
    }
    @RequestMapping(value = "/edit-add-{type}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult addStudentOrTeacher(String jsondata, @PathVariable String type, @ModelAttribute DefenseTask defenseTask){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(jsondata)){
            try{
                result.setStatus(EUResult.OK);
                if("student".equals(type)){
                    defenseTaskService.addStudentByJSON(jsondata,defenseTask);
                }else if("teacher".equals(type)){
                    defenseTaskService.addTeacherByJSON(jsondata,defenseTask);
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
}
