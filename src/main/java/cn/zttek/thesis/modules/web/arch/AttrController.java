package cn.zttek.thesis.modules.web.arch;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.factory.AttrFactory;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.service.ProxyService;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @描述: 论文相关信息（论文研究方向、论文来源、论文属性）的控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-12 17:09
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/attr")
public class AttrController extends BaseController{

    @Autowired
    private ProxyService proxyService;
    @Autowired
    private AttrFactory attrFactory;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/attr/list";
    }

    @RequestMapping(value = "/list-{op}.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(@PathVariable String op) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Org org = ThesisParam.getCurrentOrg();
        List<BaseModel> list = proxyService.listAll(op, org.getId());
        result.setTotal(list.size());
        result.setRows(list);
        return result;
    }

    @RequestMapping(value = {"/add-{op}", "/edit-{op}"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable String op, Long id) throws Exception {
        model.addAttribute("op", op);
        model.addAttribute("label", attrFactory.getLabel(op));
        if(id != null && id > 0) {
            BaseModel p = proxyService.queryById(op, id);
            model.addAttribute("id", p.getId());
            model.addAttribute("value", attrFactory.getValue(op, p));
        }
        return "console/attr/edit";
    }

    @RequestMapping(value = "/edit-{op}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@PathVariable String op, Long id, String value) {
        Org org = ThesisParam.getCurrentOrg();
        EUResult result = new EUResult();
        try {
            if (proxyService.checkValue(op, id, value, org.getId())) {
                result = EUResult.build(EUResult.FAIL, "" + attrFactory.getLabel(op) + "已存在，请重新输入！");
            } else {
                if (id != null && id > 0) {
                    proxyService.update(op, id, value, org.getId());
                } else {
                    proxyService.insert(op, value, org.getId());
                }
                result.setStatus(EUResult.OK);
                result.setMsg("" + attrFactory.getLabel(op) + "信息保存成功！");
            }
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/delete-{op}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult delete(@PathVariable String op, String ids) {
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = "";
                for (Long id : idsArry) {
                    msg = msg + proxyService.deleteOnCheck(op, id) + "<br> ";
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除" + attrFactory.getLabel(op) + "时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的" + attrFactory.getLabel(op) + "！");
        }
        return result;
    }
}
