package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Midcheck;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.MidcheckService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 论文中期检查管理相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-26 16:13
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tmidcheck")
public class ThesisMidcheckController extends BaseController {

    @Autowired
    private MidcheckService midcheckService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public Midcheck get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return midcheckService.queryById(id);
        }else
        {
            return new Midcheck();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        model.addAttribute("expands", midcheckService.listOfMidcheck(project.getId(), teacher.getId()));
        return "console/thesis/midcheck/list";
    }


    @SuppressWarnings("Duplicates")
    private void loadData(Midcheck midcheck, Model model) throws Exception {
        Thesis thesis = thesisService.queryById(midcheck.getThesisid());
        User teacher = userService.getDetail(thesis.getTeacherid());
        teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
        User student = userService.getDetail(thesis.getStudentid());
        model.addAttribute("thesis", thesis);
        model.addAttribute("teacher", teacher);
        model.addAttribute("student", student);
    }

    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute Midcheck midcheck, Model model) throws Exception{
        loadData(midcheck, model);
        return "console/thesis/midcheck/view";
    }


    @RequestMapping(value = {"/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Midcheck midcheck, Model model) throws Exception{
        loadData(midcheck, model);
        return "console/thesis/midcheck/edit";
    }


    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Midcheck midcheck, Boolean confirm) {
        EUResult result = new EUResult();
        try {
            midcheck.setTconfirm(confirm);
            if(midcheck.getId() != null && midcheck.getId() > 0){
                midcheckService.update(midcheck);
            }else{
                midcheckService.insert(midcheck);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("论文题目中期检查报告保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/confirm", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult confirm(Long midcheckid) {
        EUResult result = new EUResult();
        try {
            User currentUser = ThesisParam.getCurrentUser();
            if(currentUser.getType() == UserType.TEACHER){
                midcheckService.updateConfirm(midcheckid, "tconfirm");
            }else {
                midcheckService.updateConfirm(midcheckid, "sconfirm");
            }
            result.setStatus(EUResult.OK);
            result.setMsg("中期检查报告确认成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }






}
