package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.enums.AdviceTarget;
import cn.zttek.thesis.modules.model.Advice;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.AdviceService;
import cn.zttek.thesis.modules.service.OrgService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mankind on 2017/8/11.
 */
@Controller
@RequestMapping("/console/advice")
public class AdviceController extends BaseController {

    @Autowired
    private AdviceService adviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgService orgService;
    @ModelAttribute("advice")
    public Advice get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return adviceService.queryById(id);
        } else {
            return new Advice();
        }
    }



    @RequestMapping(value = "/{type}-list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model, @PathVariable String type) throws Exception{
        model.addAttribute("type", type);
        if("admin".equals(type)){
            model.addAttribute("orgid", ThesisParam.getCurrentOrg().getId());
        }else if("super".equals(type)){
            model.addAttribute("orgs",orgService.listAll());
        }
        return "console/advice/list";
    }
    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, Long orgid, String keywords) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        Org org = ThesisParam.getCurrentOrg();
        //如果没有传进来的orgid，则默认传入自己的机构id

        if(orgid==null||orgid==-1){
//            orgid=(org.getId()==0?null:org.getId());
              orgid= ThesisParam.getCurrentUser().getOrgid();
        }

        PageInfo<Advice> pageInfo = adviceService.listAdvice(page, rows,orgid, keywords);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Advice advice, Model model) throws Exception {
        model.addAttribute("advice", advice);
        model.addAttribute("types", AdviceTarget.values());
        return "console/advice/edit";
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Advice advice, HttpServletRequest request) {
        EUResult result = new EUResult();
        Org org = ThesisParam.getCurrentOrg();
        try {
                //如果是编辑
                if (advice.getId() != null && advice.getId() > 0) {
                    adviceService.update(advice);
                } else {
                    //如果是添加
                    advice.setOrgid(ThesisParam.getCurrentUser().getOrgid());
                    advice.setCreator(ThesisParam.getCurrentUser().getId());
                    adviceService.insert(advice);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("公告信息保存成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult delete(String ids){
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = "";
                for (Long id : idsArry) {
                    msg = msg + adviceService.deleteById(id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除公告时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的公告！");
        }
        return result;
    }
    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute Advice advice, Model model) throws Exception {
        if(advice.getCreator() != null && advice.getCreator()  > 0){
            User creator = userService.getDetail(advice.getCreator());
            model.addAttribute("user", creator);
        }
        return "console/advice/view";
    }



    @RequestMapping(value = "/showlist.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult showlist(Integer page, Integer rows, String keywords) throws Exception{
        EUDataGridResult result = new EUDataGridResult();
        User user = ThesisParam.getCurrentUser();
        Long orgid=user.getOrgid().equals(0)?null:user.getOrgid();
        PageInfo<Advice> pageInfo = adviceService.listByUserType(page, rows,orgid,keywords,user.getType());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }


}
