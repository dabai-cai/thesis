package cn.zttek.thesis.modules.web.arch;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Title;
import cn.zttek.thesis.modules.service.TitleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @描述: 职称管理控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-25 16:43
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/title")
public class TitleController extends BaseController {

    @Autowired
    private TitleService titleService;


    @ModelAttribute("title")
    public Title get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return titleService.queryById(id);
        } else {
            return new Title();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/title/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list() throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Title> list = titleService.listAll();
        result.setTotal(list.size());
        result.setRows(list);
        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Title title, Model model) throws Exception {
        model.addAttribute("levels", TitleLevel.values());
        model.addAttribute("title", title);
        return "console/title/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Title title, HttpServletRequest request) {

        EUResult result = new EUResult();
        try {
            if (titleService.checkName(title)) {
                result = EUResult.build(EUResult.FAIL, "职称名称已存在，请重新输入！");
            } else {
                if (title.getId() != null && title.getId() > 0) {
                    titleService.update(title);
                } else {
                    titleService.insert(title);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("职称信息保存成功！");
            }

            TitleHolder.update(title);
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
                    msg = msg + titleService.deleteOnCheck(id) + "<br> ";
                    TitleHolder.remove(id);
                }
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除职称时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的职称！");
        }
        return result;
    }

}
