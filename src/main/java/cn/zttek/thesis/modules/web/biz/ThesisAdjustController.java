package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @描述: 学生选题调整相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-10-16 17:24
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tadjust")
public class ThesisAdjustController extends BaseController{


    @Autowired
    private UserService userService;
    @Autowired
    private ThesisService thesisService;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(){
        return "console/thesis/adjust/list";
    }

    @RequestMapping(value = "/wizard", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult wizard(Integer step_number, HttpServletRequest request) throws Exception{
        EUResult result = null;
        Project currentProj = ThesisParam.getCurrentProj();
        //校验学生信息和当前选题信息
        if(step_number == 1){
            String stuno = request.getParameter("stuno");
            if(StringUtils.isNotEmpty(stuno)){
                User student = userService.getByAccount(stuno);
                if(student != null){
                    Thesis thesis = thesisService.getStudentThesis(currentProj.getId(), student.getId());
                    User teacher = null;
                    if(thesis != null){
                        teacher = userService.getDetail(thesis.getTeacherid());
                        teacher.getInfo().setTitle(TitleHolder.getTitle(teacher.getInfo().getTid()));
                    }
                    result = EUResult.build(EUResult.OK, "学生选题信息加载成功！", new Object[]{student, thesis, teacher});
                }else{
                    result = EUResult.build(EUResult.FAIL, "学生学号输入不正确，系统不存在此学生信息！");
                }
            }else{
                result = EUResult.build(EUResult.FAIL, "请输入学生学号！");
            }
        }

        return result;
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult update(Long thesisid, Long studentid){
        EUResult result = null;
        try {
            thesisService.updateStudentThesis(thesisid, studentid);
            result = EUResult.build(EUResult.OK, "学生新选题结果保存成功！");
        } catch (Exception e) {
            result = EUResult.build(EUResult.FAIL, e.getMessage());
        }
        return result;
    }
}
