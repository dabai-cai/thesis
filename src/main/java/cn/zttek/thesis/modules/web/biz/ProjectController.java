package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Title;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.ProjectService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @描述: 论文工作前端控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-24 16:43
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/project")
public class ProjectController extends BaseController {


    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private TitleService titleService;


    @ModelAttribute("project")
    public Project get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return projectService.queryById(id);
        } else {
            return new Project();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/project/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Org org = ThesisParam.getCurrentOrg();
        PageInfo<Project> pageInfo = projectService.listByPage(page, rows, org);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Project project, Model model) throws Exception {
        model.addAttribute("project", project);
        return "console/project/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Project project, HttpServletRequest request) {
        EUResult result = new EUResult();
        Org org = ThesisParam.getCurrentOrg();
        try {
            if (projectService.checkTitle(project)) {
                result = EUResult.build(EUResult.FAIL, "论文工作名称已存在，请重新输入！");
            } else {
                if (project.getId() != null && project.getId() > 0) {
                    projectService.update(project);
                } else {
                    if(projectService.checkActiveCount(org)){
                        result = EUResult.build(EUResult.FAIL, "当前活跃的论文工作数量已达到最大限制，不能添加！");
                    }
                    projectService.insert(project, org, getDocRoot(request));
                }
                result.setStatus(EUResult.OK);
                result.setMsg("论文工作信息保存成功！");
            }
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult delete(String ids) {
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = "";
                for (Long id : idsArry) {

                    //TODO 完善delete方法
                    //msg = msg + projectService.deleteOnCheck(id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除论文工作时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的论文工作！");
        }
        return result;
    }

    /********************************以下为论文工作参与用户的管理功能************************************/



    @RequestMapping(value = "/students", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listStudents() throws Exception {
        return "console/project/students";
    }

    @RequestMapping(value = "/teachers", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listTeachers() throws Exception {
        return "console/project/teachers";
    }

    @RequestMapping(value = "/users-{userType}.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, String keywords, @PathVariable String userType,
                                 String major, Integer grade, String clazz) throws Exception {

        return selectUser(page, rows, keywords, userType, major, grade, clazz, true);

    }

    @RequestMapping(value = {"/select-{userType}"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String selectUser(Model model, @PathVariable String userType) throws Exception {
        model.addAttribute("type", userType);
        return "console/project/select";
    }

    @RequestMapping(value = "/select-{userType}.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult selectUser(Integer page, Integer rows, String keywords, @PathVariable String userType,
                                 String major, Integer grade, String clazz, Boolean selected) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        UserType type = "teacher".equals(userType) ? UserType.TEACHER : UserType.STUDENT;
        Project project = ThesisParam.getCurrentProj();
        Org org = ThesisParam.getCurrentOrg();
        List<String> args = new ArrayList<>(3);
        if(type == UserType.STUDENT){
            args.add(major);
            if(grade != null) {
                args.add(grade + "");
            }else{
                args.add(null);
            }
            args.add(clazz);
        }
        PageInfo<User> pageInfo = userService.listByPage(page, rows, org, project, keywords, type, args, selected);
        if(type == UserType.TEACHER){
            for (User user : pageInfo.getList()){
                user.getInfo().setTitle(TitleHolder.getTitle(user.getInfo().getTid()));
            }
        }
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }


    @RequestMapping(value = "/save-{userType}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult saveUser(String ids, @PathVariable String userType) {
        EUResult result = new EUResult();
        UserType type = "teacher".equals(userType) ? UserType.TEACHER : UserType.STUDENT;
        Project project = ThesisParam.getCurrentProj();
        Org org = ThesisParam.getCurrentOrg();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = userService.saveSelect(org, project, type, idsArry);
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("添加" + type.getLabel() + "到论文工作中时发生异常！" + e.getMessage());
            }
        }else{
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要添加到论文工作中的" + type.getLabel() + "！");
        }
        return result;
    }

    @RequestMapping(value = "/saveQuery-{userType}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult saveQuery( @PathVariable String userType, String keywords, String major, Integer grade, String clazz, Boolean selected) {
        EUResult result = new EUResult();
        UserType type = "teacher".equals(userType) ? UserType.TEACHER : UserType.STUDENT;
        Project project = ThesisParam.getCurrentProj();
        Org org = ThesisParam.getCurrentOrg();
        List<String> args = new ArrayList<>(3);
        if(type == UserType.STUDENT){
            args.add(major);
            args.add(grade != null? grade + "" : null);
            args.add(clazz);
        }
        try {
            result.setStatus(EUResult.OK);
            String msg = userService.saveQuery(org, project, keywords, type, args, selected);
            result.setMsg(msg);
        } catch (Exception e) {
            result.setStatus(EUResult.FAIL);
            result.setMsg("添加" + type.getLabel() + "到论文工作中时发生异常！" + e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/delete-{userType}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult deleteUser(String ids, @PathVariable String userType) {
        EUResult result = new EUResult();
        UserType type = "teacher".equals(userType) ? UserType.TEACHER : UserType.STUDENT;
        Project project = ThesisParam.getCurrentProj();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                userService.deleteProjectUsers(project, idsArry, type);
                result.setStatus(EUResult.OK);
                result.setMsg("删除" + type.getLabel() + "成功！");
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除" + type.getLabel() + "发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的" + type.getLabel() + "！");
        }
        return result;
    }
}
