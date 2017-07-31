package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Taskbook;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.ApplyService;
import cn.zttek.thesis.modules.service.TaskbookService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * @描述: 论文任务书管理相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-26 16:13
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/ttask")
public class ThesisTaskController extends BaseController {

    @Autowired
    private TaskbookService taskbookService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public Taskbook get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return taskbookService.queryById(id);
        }else
        {
            return new Taskbook();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        model.addAttribute("expands", taskbookService.listOfTask(project.getId(), teacher.getId()));
        return "console/thesis/task/list";
    }


    @SuppressWarnings("Duplicates")
    private void loadData(Taskbook taskbook, Model model) throws Exception {
        Thesis thesis = thesisService.queryById(taskbook.getThesisid());
        User teacher = userService.getDetail(thesis.getTeacherid());
        teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
        User student = userService.getDetail(thesis.getStudentid());
        model.addAttribute("thesis", thesis);
        model.addAttribute("teacher", teacher);
        model.addAttribute("student", student);
    }

    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute Taskbook taskbook, Model model) throws Exception{
        loadData(taskbook, model);
        return "console/thesis/task/view";
    }


    @RequestMapping(value = {"/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Taskbook taskbook, Model model) throws Exception{
        loadData(taskbook, model);
        return "console/thesis/task/edit";
    }


    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Taskbook taskbook, Boolean confirm) {
        EUResult result = new EUResult();
        try {
            taskbook.setTconfirm(confirm);
            if(taskbook.getId() != null && taskbook.getId() > 0){
                taskbookService.update(taskbook);
            }else{
                taskbookService.insert(taskbook);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("论文题目任务书保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/confirm", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult confirm(Long taskid) {
        EUResult result = new EUResult();
        try {
            User currentUser = ThesisParam.getCurrentUser();
            if(currentUser.getType() == UserType.TEACHER){
                taskbookService.updateConfirm(taskid, "tconfirm");
            }else {
                taskbookService.updateConfirm(taskid, "sconfirm");
            }
            result.setStatus(EUResult.OK);
            result.setMsg("任务书确认成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }






}
