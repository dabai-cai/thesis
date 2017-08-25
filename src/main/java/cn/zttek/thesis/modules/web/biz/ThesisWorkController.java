package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.*;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @描述: 学生论文工作事务管理相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-11-01 14:24
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/twork")
public class ThesisWorkController extends BaseController {

    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskbookService taskbookService;
    @Autowired
    private MidcheckService midcheckService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private GoodDelayService goodDelayService;
    @Autowired
    private DefenseGroupService defenseGroupService;

    @RequestMapping(value = "/index", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String index(Model model) throws Exception{
        Project currentProj = ThesisParam.getCurrentProj();
        User currentUser = ThesisParam.getCurrentUser();
        if(currentUser.getType() != UserType.STUDENT){
            throw new Exception("您当前身份不允许访问该功能，请注销重新登录！");
        }

        Thesis thesis = thesisService.getStudentThesis(currentProj.getId(), currentUser.getId());
        if(thesis != null){
            model.addAttribute("thesis", thesis);
            User teacher = userService.getDetail(thesis.getTeacherid());
            teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
            model.addAttribute("teacher", teacher);
            Taskbook taskbook = taskbookService.getByThesis(thesis.getId());
            model.addAttribute("taskbook", taskbook);
            Midcheck midcheck = midcheckService.getByThesis(thesis.getId());
            model.addAttribute("midcheck", midcheck);
            Project project=ThesisParam.getCurrentProj();
            model.addAttribute("project",project);
            Upload upload=uploadService.selectByThesis(thesis.getId());
            model.addAttribute("upload",upload);
            GoodDelay goodDelay=goodDelayService.queryByThesisId(thesis.getId());
            model.addAttribute("goodDelay",goodDelay);
            DefenseGroup defenseGroup=defenseGroupService.getByStudent(project.getId(),currentUser.getId());
            model.addAttribute("defenseGroup",defenseGroup);
            //TODO 加载答辩小组信息、评分情况等
        }
        return "console/thesis/work/index";
    }


}
