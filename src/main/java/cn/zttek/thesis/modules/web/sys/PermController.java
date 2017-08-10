package cn.zttek.thesis.modules.web.sys;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.easyui.EUTreeNode;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.model.Permission;
import cn.zttek.thesis.modules.model.Resource;
import cn.zttek.thesis.modules.service.PermissionService;
import cn.zttek.thesis.modules.service.ResourceService;
import cn.zttek.thesis.modules.service.RoleService;
import cn.zttek.thesis.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @描述:
 * @作者: Pengo.Wen
 * @日期: 2016-08-16 14:50
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/perm")
public class PermController extends BaseController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ResourceService resourceService;
    /**
     * 注入权限信息<br/>
     * 这个方法会在请求进入该控制器的某个方法前调用，<br/>
     * 比如修改权限：在进入edit()--POST 前会执行此方法返回一个permission，然后再将表单的数据放入这个permission<br/>
     *
     * @param id
     * @return
     */
    @ModelAttribute("permission")
    public Permission get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return permissionService.queryById(id);
        } else {
            return new Permission();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/perm/list";
    }

    @RequestMapping(value = "/listByRes.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Long resid) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Permission> list = permissionService.listByRes(resid);
        result.setTotal(list.size());
        result.setRows(list);

        return result;
    }

    @RequestMapping(value = "/listByRole.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult listByRole(Long rid) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Permission> list = permissionService.listByRole(rid);
        result.setTotal(list.size());
        result.setRows(list);

        return result;
    }



    //
    @RequestMapping(value = "/tree.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUTreeNode> getTree() throws Exception {
        List<EUTreeNode> results = new ArrayList<>();
        List<Resource> list = permissionService.getPermTree();
        for (int i = 0; i < list.size(); i++) {
            Resource parent = list.get(i);
            EUTreeNode result = new EUTreeNode();
            result.setId(parent.getId() * 1000);
            result.setText(parent.getName());
            result.setState("open");
            result.setLeaf(false);
            each(parent, result);
            results.add(result);
        }

        return results;
    }

    private static void each(Resource parent, EUTreeNode result){
        if (parent != null){
            if(!parent.getSub().isEmpty()) {
                result.setChildren(new ArrayList<>());
                for (Resource child : parent.getSub()){
                    EUTreeNode node = new EUTreeNode();
                    node.setId(child.getId() * 1000);
                    node.setText(child.getName());
                    node.setState("closed");
                    node.setLeaf(false);
                    each(child, node);
                    result.getChildren().add(node);
                }
            }else{
                if(parent.getPerms() != null) {
                    result.setChildren(new ArrayList<>());
                    for (Permission perm : parent.getPerms()) {
                        EUTreeNode node = new EUTreeNode();
                        node.setId(perm.getId());
                        node.setText(perm.getName());
                        node.setLeaf(true);
                        result.getChildren().add(node);
                    }
                }
            }
        }
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Permission permission, Model model) throws Exception {
        List<Resource> list = resourceService.listAll();
        model.addAttribute("resources", list);
        model.addAttribute("perm", permission);
        return "console/perm/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Permission permission) {

        EUResult result = new EUResult();
        try {
            if (permissionService.checkKeystr(permission)) {
                result = EUResult.build(EUResult.FAIL, "权限编码已存在，请重新输入！");
            } else {
                if (permission.getId() != null && permission.getId() > 0) {
                    permissionService.update(permission);
                    //清除权限系统的缓存
                    ShiroUtils.clearAllCachedAuthorizationInfo();
                } else {
                    permissionService.insert(permission);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("权限信息保存成功！");
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
                String msg = "";
                for (Long id : idsArry) {
                    msg = msg + permissionService.deleteOnCheck(id) + "<br>";
                }
                result.setMsg(msg + "");
                //TODO 为当前用户重新加载菜单
                //清除权限系统的缓存
                ShiroUtils.clearAllCachedAuthorizationInfo();
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除权限时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的权限！");
        }
        return result;
    }

}
