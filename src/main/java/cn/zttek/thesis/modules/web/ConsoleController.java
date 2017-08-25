package cn.zttek.thesis.modules.web;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.AdviceService;
import cn.zttek.thesis.modules.service.OrgService;
import cn.zttek.thesis.modules.service.ProjectService;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Pengo on 2015/11/14.
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends BaseController {


    @Autowired
    private OrgService orgService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AdviceService adviceService;

    @RequiresAuthentication
    @RequestMapping(value = "/index", produces = "text/html;charset=utf-8")
    public String index(Model model, HttpSession session) throws Exception{
        User user = ThesisParam.getCurrentUser();
        if(user.getType() == UserType.SUPER){
            List<Org> orgs  = orgService.listAll();
            model.addAttribute("orgs", orgs);
            if(orgs.isEmpty()){
                return "redirect:/console/main";
            }
        }else{

            //TODO 如果不是超级管理员，则直接设置为当前用户的组织机构，看需不需要选择论文工作
            Org org = orgService.queryById(user.getOrgid());
            model.addAttribute("orgs", Arrays.asList(org));
            /*if(user.getType() == UserType.STUDENT){
                session.setAttribute("currentOrg", org);
                List<Project> projects = projectService.listByUser(org.getId(), user.getId(), "student");
                if(projects.size() > 0){
                    session.setAttribute("currentProj", projects.get(0));
                }
            }*/
        }
        return "console/index";
    }

    @RequiresAuthentication
    @RequestMapping(value = "/index", produces = "text/html;charset=utf-8", method = RequestMethod.POST)
    public String index(Long orgid, Long projid, HttpSession session) throws Exception{
        if(orgid != null){
            Org org = orgService.queryById(orgid);
            session.setAttribute("currentOrg", org);
        }
        if(projid != null){
            Project project = projectService.queryById(projid);
            session.setAttribute("currentProj", project);
        }
        return "redirect:/console/main";
    }


    @RequestMapping(value = "/switchproj", produces = "text/html;charset=utf-8")
    public String switchPro(Model model,Long orgid) {
        model.addAttribute("orgid",orgid);
        return "/console/switchproj";
    }

    @RequestMapping(value = "/listProject.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult listProject(Long orgid) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        User currentUser = ThesisParam.getCurrentUser();
        List<Project> projects;
        if(currentUser.getType() == UserType.ADMIN || currentUser.getType() == UserType.SUPER) {
            projects = projectService.listByOrg(orgid);
        }else{
            projects = projectService.listByUser(orgid, currentUser.getId(), currentUser.getType().name().toLowerCase());
        }
        result.setTotal(projects.size());
        result.setRows(projects);
        return result;
    }

    //@RequiresAuthentication
    @RequestMapping(value = "/main", produces = "text/html;charset=utf-8",method = RequestMethod.GET)
    public String main(Model model) throws Exception {
        //TODO 取出菜单
        User currentUser=ThesisParam.getCurrentUser();
        Org currentOrg=ThesisParam.getCurrentOrg();
        model.addAttribute("advices",adviceService.listByUserType(1,10,currentOrg.getId(),null,currentUser.getType()).getList());
        return "console/main";
    }

    //@RequiresAuthentication
    @RequestMapping(value = "/home", produces = "text/html;charset=utf-8")
    public String home() throws Exception {
        return "console/home";
    }

}
