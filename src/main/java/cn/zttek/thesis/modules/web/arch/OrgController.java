package cn.zttek.thesis.modules.web.arch;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.service.OrgService;
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
 * @描述: 组织机构前端控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-24 16:43
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/org")
public class OrgController extends BaseController {


    @Autowired
    private OrgService orgService;


    @ModelAttribute("org")
    public Org get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return orgService.queryById(id);
        } else {
            return new Org();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/org/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        PageInfo<Org> pageInfo = orgService.listByPage(page, rows);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Org org, Model model) throws Exception {
        model.addAttribute("org", org);
        return "console/org/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Org org, HttpServletRequest request) {

        EUResult result = new EUResult();
        try {
            if (orgService.checkName(org)) {
                result = EUResult.build(EUResult.FAIL, "组织机构名称已存在，请重新输入！");
            } else {
                if (org.getId() != null && org.getId() > 0) {
                    orgService.update(org);
                } else {
                    orgService.insert(org, getDocRoot(request));
                }
                result.setStatus(EUResult.OK);
                result.setMsg("组织机构信息保存成功！");
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
                String msg = "组织删除成功！";
                for (Long id : idsArry) {
                    //TODO 完善delete方法
                    //msg = msg + orgService.deleteOnCheck(id) + "<br> ";

                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除组织机构时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的组织机构！");
        }
        return result;
    }
}
