package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.ApplyService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @描述: 教师确认选题管理控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-21 15:54
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/treply")
public class ThesisReplyController extends BaseController {

    @Autowired
    private ThesisService thesisService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;


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
    public String list() throws Exception{
        return "console/thesis/reply/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        PageInfo<Apply> pageInfo = applyService.listByTeacher(page, rows, project.getId(), teacher.getId());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Apply apply, Model model) throws Exception{
        Thesis thesis = thesisService.queryById(apply.getThesisid());
        User student = userService.getDetail(apply.getStudentid());
        model.addAttribute("thesis", thesis);
        model.addAttribute("student", student);
        return "console/thesis/reply/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Apply apply, Boolean confirm) {
        EUResult result = new EUResult();
        try {
            if(confirm){
                Project project = ThesisParam.getCurrentProj();
                Thesis thesis = thesisService.getStudentThesis(project.getId(),apply.getStudentid());
                if(thesis != null && thesis.getId() != apply.getThesisid()){
                    return EUResult.build(EUResult.FAIL, "该学生已入选题目[" + thesis.getTopic() + "]，本题不能再指定该学生!");
                }
            }
            apply.setReplyTime(new Timestamp(System.currentTimeMillis()));
            applyService.update(apply, confirm);
            result.setStatus(EUResult.OK);
            result.setMsg("题目申请答复保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

}
