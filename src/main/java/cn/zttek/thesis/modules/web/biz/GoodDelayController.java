package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.expand.ApplyExtend;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.GoodDelayService;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value ="/console/gooddelay")
public class GoodDelayController {
    @Autowired
    private GoodDelayService goodDelayService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public GoodDelay get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return goodDelayService.queryById(id);
        }else {
            return new GoodDelay();
        }
    }


    @RequestMapping(value = "edit-{type}", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute GoodDelay goodDelay, Model model, @PathVariable String type) throws Exception{
        loadData(goodDelay,model);
        return "/console/thesis/gooddelay/"+type;
    }


    //学生申请,修改
    @RequestMapping(value = "/student",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public EUResult studentConfirm(@ModelAttribute GoodDelay goodDelay) throws  Exception {
        EUResult euResult = new EUResult();
        try {
            if(null!=goodDelay.getId()){
                goodDelayService.update(goodDelay);
                euResult.setMsg("编辑成功!");
                euResult.setStatus(EUResult.OK);
                return euResult;
            }
            goodDelayService.insert(goodDelay);
            euResult.setMsg("申请成功!");
            euResult.setStatus(EUResult.OK);
        } catch (Exception e) {
            euResult.setStatus(EUResult.FAIL);
            euResult.setMsg(e.getMessage());
        }
        return euResult;

    }

    //教师、院系编辑
    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute GoodDelay goodDelay) {
        EUResult result = new EUResult();
        try {
            goodDelayService.update(goodDelay);
            if(goodDelay.getOrgconf()==1){
                Thesis thesis=thesisService.queryById(goodDelay.getThesisid());
                if(goodDelay.getApplytype()==-1){
                    //延期答辩
                    thesis.setDefensestatus(DefenseStatus.DELAY);
                }else{
                    thesis.setDefensestatus(DefenseStatus.EXCEL);
                }
                //更新论文答辩类型
                thesisService.update(thesis);
            }
            result.setMsg("编辑申请成功!");
            result.setStatus(EUResult.OK);
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/teacher", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list1(Model model) throws Exception{
        User teacher = ThesisParam.getCurrentUser();
        List<ApplyExtend> applys=goodDelayService.queryByTeacher(teacher.getId());
        Integer count=goodDelayService.countUnConfByTeacher(teacher.getId());
        model.addAttribute("count",count);
        model.addAttribute("applys",applys);
        List<Thesis> theses=new ArrayList<Thesis>();
        return "console/thesis/gooddelay/list1";
    }


    @RequestMapping(value = "/org", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list2(Model model) throws Exception{
        Org org = ThesisParam.getCurrentOrg();
        model.addAttribute("applys",goodDelayService.queryByOrg(org.getId()));
        return "console/thesis/gooddelay/list2";
    }



    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute GoodDelay goodDelay, Model model) throws Exception{
        loadData(goodDelay, model);
        return "console/thesis/gooddelay/view";
    }

    /**
     * @param goodDelay
     * @param model
     * @throws Exception
     */

    private void loadData(GoodDelay goodDelay,Model model) throws Exception {
         Thesis thesis=thesisService.queryById(goodDelay.getThesisid());
         model.addAttribute("thesis",thesis);
         model.addAttribute("student",userService.getDetail(thesis.getStudentid()));
         model.addAttribute("goodDelay",goodDelay);
    }
}
