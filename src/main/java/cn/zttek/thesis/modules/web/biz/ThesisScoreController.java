package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.ScoreService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @描述: 论文成绩管理相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-10-12 20:06
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tscore")
public class ThesisScoreController extends BaseController {


    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;


    @ModelAttribute
    public Score get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return scoreService.queryById(id);
        }else
        {
            return new Score();
        }
    }

    @RequestMapping(value = "/list1", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list1(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        model.addAttribute("expands", scoreService.listByTeacher(project.getId(), teacher.getId()));
        return "console/thesis/score/list1";
    }

    @RequestMapping(value = "/list2", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list2(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User viewer = ThesisParam.getCurrentUser();
        model.addAttribute("expands", scoreService.listByViewer(project.getId(), viewer.getId()));
        return "console/thesis/score/list2";
    }

    @RequestMapping(value="/list3", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list3(Model model)throws Exception{
        User secretary = ThesisParam.getCurrentUser();
        Project project = ThesisParam.getCurrentProj();
        model.addAttribute("expands", scoreService.listBySecretary(project.getId(),secretary.getId()));
        return "console/thesis/score/list3";
    }

    private void loadData(Score score, Model model) throws Exception {
        Thesis thesis = thesisService.queryById(score.getThesisid());
        //指导老师资料
        User teacher = userService.getDetail(thesis.getTeacherid());
        teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
        if(thesis.getViewerid() != null && thesis.getViewerid() > 0){
            //评阅老师资料
            User viewer = userService.getDetail(thesis.getViewerid());
            viewer.getInfo().setTitle(TitleHolder.getTitle(viewer.getInfo().getTid()));
            model.addAttribute("viewer", viewer);
        }
        //学生信息
        User student = userService.getDetail(thesis.getStudentid());
        model.addAttribute("thesis", thesis);
        model.addAttribute("teacher", teacher);
        model.addAttribute("student", student);
    }

    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute Score score, Model model) throws Exception{
        loadData(score, model);
        model.addAttribute("general",scoreService.general(score));
        model.addAttribute("level",scoreService.thesisLevel(score));
        model.addAttribute("agree",scoreService.agree(score));
        return "console/thesis/score/view";
    }

    @RequestMapping(value = {"/edit-{mark}"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Score score, Model model, @PathVariable String mark) throws Exception{
        loadData(score, model);
        return "console/thesis/score/" + mark;
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Score score) {
        EUResult result = new EUResult();
        try {

            if(score.getId() != null && score.getId() > 0){
                if(score.getMark2()!=null&&score.getMark3()==null){//指导老师，评阅老师登记成绩之后，更新论文答辩类型(正常答辩)
                    Thesis thesis=thesisService.queryById(score.getThesisid());
                    thesis.setDefensestatus(DefenseStatus.NORMAL);
                    thesisService.update(thesis);
                }
                scoreService.update(score);
            }else{
                scoreService.insert(score);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("论文成绩保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/assign", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult assign(Long viewerid, String ids) {
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                scoreService.saveAssign(viewerid, idsArry);
                result.setStatus(EUResult.OK);
                result.setMsg("指定评阅教师成功！");
            } catch (Exception ex) {
                result.setStatus(EUResult.FAIL);
                result.setMsg(ex.getMessage());
            }
        }else{
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要指定评阅教师的论文题目");
        }
        return result;
    }
}
