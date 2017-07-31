package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.ApplyService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @描述: 学生选题管理控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-21 15:54
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tapply")
public class ThesisApplyController extends BaseController {

    @Autowired
    private ThesisService thesisService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private TitleService titleService;


    @ModelAttribute
    public Apply get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return applyService.queryById(id);
        }else
        {
            return new Apply();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User student = ThesisParam.getCurrentUser();
        model.addAttribute("applies", applyService.listByStudent(project.getId(), student.getId()));
        return "console/thesis/apply/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, Long teacherid, String keywords) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Project project = ThesisParam.getCurrentProj();
        PageInfo<Thesis> pageInfo = thesisService.listNotSelect(page, rows, project.getId(), teacherid, keywords);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Apply apply, Long thesisid, Model model) throws Exception{
        if(thesisid != null){
            Thesis thesis = thesisService.queryById(thesisid);
            User teacher = userService.getDetail(thesis.getTeacherid());
            teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
            model.addAttribute("teacher", teacher);
            model.addAttribute("thesis", thesis);
        }
        return "console/thesis/apply/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Apply apply) {
        EUResult result = new EUResult();
        Project project = ThesisParam.getCurrentProj();
        User student = ThesisParam.getCurrentUser();
        try {
            if (apply.getId() != null && apply.getId() > 0) {
                applyService.update(apply);
            } else {
                if(applyService.checkMaxAllowed(project, student.getId())){
                    result = EUResult.build(EUResult.FAIL, "你申请的论文题目数量已达到论文工作的最大允许量，不能申请！");
                }
                apply.setProjid(project.getId());
                apply.setStudentid(student.getId());
                apply.setApplyTime(new Timestamp(System.currentTimeMillis()));
                applyService.insert(apply);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("论文题目申请保存成功！");
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
                    msg = msg + applyService.deleteOnCheck(id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除论文题目申请时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的论文题目申请！");
        }
        return result;
    }

    @RequestMapping(value = "/result", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String result(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User student = ThesisParam.getCurrentUser();
        List<Apply> applies = applyService.listByStudent(project.getId(), student.getId());
        Thesis thesis = thesisService.getStudentThesis(project.getId(), student.getId());
        if(thesis != null){
            Apply apply = null;
            for (int i = 0; i < applies.size(); i++){
                Apply ap = applies.get(i);
                if(ap.getThesisid() == thesis.getId() && ap.getStudentid() == thesis.getStudentid()){
                    applies.remove(i);
                    apply = ap;
                    break;
                }
            }
            User teacher = userService.getDetail(thesis.getTeacherid());
            teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
            model.addAttribute("teacher", teacher);
            model.addAttribute("thesis", thesis);
            model.addAttribute("apply", apply);
        }
        model.addAttribute("applies", applies);
        return "console/thesis/apply/result";
    }
}
