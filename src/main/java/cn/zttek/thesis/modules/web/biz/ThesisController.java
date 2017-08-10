package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.*;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @描述: 论文题目相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-14 14:28
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/thesis")
public class ThesisController extends BaseController {

    @Autowired
    private ThesisService thesisService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProxyService proxyService;

    @ModelAttribute
    public Thesis get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return thesisService.queryById(id);
        }else{
            return new Thesis();
        }
    }

    @RequestMapping(value = "/listAll", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listAll(Model model) throws Exception{
        Org org = ThesisParam.getCurrentOrg();
        List<Project> projects = projectService.listByOrg(org.getId());
        model.addAttribute("projects", projects);
        return "console/thesis/listAll";
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(){
        return "console/thesis/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, Long projid, String keywords) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        boolean isCurrentProj = false;
        if(projid != null && projid == 0){
            projid = null;
        }else if(projid == null){
            projid = ThesisParam.getCurrentProj().getId();
            isCurrentProj = true;
        }
        Org org = ThesisParam.getCurrentOrg();
        User teacher = ThesisParam.getCurrentUser();
        PageInfo pageInfo = thesisService.listByPage(page, rows, org.getId(), projid, teacher.getId(), keywords, isCurrentProj);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute Thesis thesis, Model model) throws Exception {
        if(thesis.getStudentid() != null && thesis.getStudentid() > 0){
            User student = userService.getDetail(thesis.getStudentid());
            model.addAttribute("student", student);
        }
        if(thesis.getTeacherid() != null && thesis.getTeacherid() > 0){
            User teacher = userService.getDetail(thesis.getTeacherid());
            teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
            model.addAttribute("teacher", teacher);
        }

        model.addAttribute("thesis", thesis);
        return "console/thesis/view";
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Thesis thesis, Model model) throws Exception {
        Org org = ThesisParam.getCurrentOrg();
        model.addAttribute("directions", proxyService.listAll("direction", org.getId()));
        model.addAttribute("sources", proxyService.listAll("source", org.getId()));
        model.addAttribute("properties", proxyService.listAll("property", org.getId()));
        if(thesis.getStudentid() != null && thesis.getStudentid() > 0){
            User student = userService.queryById(thesis.getStudentid());
            model.addAttribute("student", student);
        }
        if(thesis.getId() == null || thesis.getId() <= 0){
            Project currentProj = ThesisParam.getCurrentProj();
            thesis.setUploadtime(new Timestamp(currentProj.getSubmitdate().getTime()));
        }
        model.addAttribute("thesis", thesis);
        return "console/thesis/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Thesis thesis) {
        EUResult result = new EUResult();
        Org org = ThesisParam.getCurrentOrg();
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        try {
            if (thesisService.checkTopic(project.getId(), thesis)) {
                result = EUResult.build(EUResult.FAIL, "论文题目已存在，请重新输入！");
            } else {
                if (thesis.getId() != null && thesis.getId() > 0) {
                    thesisService.update(thesis);
                } else {
                    if(thesisService.checkMaxAllowed(project, teacher.getId())){
                        result = EUResult.build(EUResult.FAIL, "你申报的论文题目数量已达到论文工作的最大要求，不能添加！");
                    }
                    thesisService.insert(org, project, teacher, thesis);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("论文题目信息保存成功！");
            }
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/checkStudent", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult checkStudent(Long studentid, Long id, String stuno){
        EUResult result = new EUResult();
        Project project = ThesisParam.getCurrentProj();
        if(id == null){
            id = 0L;
        }
        try {
            User student = null;
            if(StringUtils.isNotEmpty(stuno)){
                student = userService.getByAccount(project.getId(), stuno);
                if(student == null){
                    result = EUResult.build(EUResult.FAIL, "您输入的学生学号不在当前论文工作中，请重新输入或跟管理员联系！");
                    return result;
                }else{
                    studentid = student.getId();
                }
            }
            if (thesisService.checkStudent(project.getId(), id, studentid)) {
                result = EUResult.build(EUResult.FAIL, "该学生已被其他教师指定，请重新选择学生！");
            } else {
                result.setStatus(EUResult.OK);
                result.setMsg("该学生没有其他题目关联！");
                if(student != null) result.setData(student);
            }
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/unsign", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult unsign(Long thesisid) {
        EUResult result = new EUResult();
        if (thesisid != null && thesisid > 0) {
            try {
                result.setStatus(EUResult.OK);
                thesisService.deleleStudent(thesisid);
                result.setMsg("论文题目取消学生成功！");
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("论文题目取消学生时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要取消学生的论文题目！");
        }
        return result;
    }

    //删除论文工作
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
                    msg = msg + thesisService.deleteOnCheck(id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除论文题目时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的论文题目！");
        }
        return result;
    }



}
