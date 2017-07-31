package cn.zttek.thesis.modules.web.sys;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Resource;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.ResourceService;
import cn.zttek.thesis.modules.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @描述: 资源管理控制类
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 17:51
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 注入资源信息。<br/>
     * 这个方法会在请求进入该控制器的某个方法前调用，<br/>
     * 比如修改资源：在进入edit()--POST 前会执行此方法返回一个resource，然后再将表单的数据放入这个resource<br/>
     *
     * @param id
     * @return
     */
    @ModelAttribute("resource")
    public Resource get(@RequestParam(required = false) Long id) throws Exception {
        try {
            if (id != null && id > 0) {
                return resourceService.queryById(id);
            } else {
                return new Resource();
            }
        } catch (Exception e) {
            return new Resource();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/resource/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list() throws Exception {
        EUDataGridResult result = new EUDataGridResult();

        List<Resource> list = resourceService.listAll();
        result.setTotal(list.size());
        result.setRows(list);

        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute(value = "resource") Resource resource, Model model) throws Exception {
        List<Resource> list = resourceService.listAll();
        model.addAttribute("resources", list);
        model.addAttribute("resource", resource);
        return "console/resource/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute(value = "resource") Resource resource) {

        EUResult result = new EUResult();
        try {
            if (resourceService.checkName(resource.getId(), resource.getName())) {
                result = EUResult.build(EUResult.FAIL, "资源名称已存在，请重新输入！");
            } else {
                if (resource.getId() != null && resource.getId() > 0) {
                    resourceService.update(resource);
                } else {
                    resourceService.insert(resource);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("资源信息保存成功！");
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
            if (idsArry.contains(1)) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("不能删除root资源！");
                return result;
            } else {
                try {
                    result.setStatus(EUResult.OK);
                    String msg = "";
                    for (Long id : idsArry) {
                        msg = msg +  resourceService.deleteOnCheck(id) + "<br>";
                    }
                    result.setMsg(msg);
                } catch (Exception e) {
                    result.setStatus(EUResult.FAIL);
                    result.setMsg("删除资源时发生异常！" + e.getMessage());
                }

            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的资源！");
        }
        return result;
    }
}
