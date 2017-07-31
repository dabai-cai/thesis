package cn.zttek.thesis.modules.web.sys;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.service.PermissionService;
import cn.zttek.thesis.modules.service.RoleService;
import cn.zttek.thesis.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @描述: 角色管理控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-17 16:11
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/role")
public class RoleController extends BaseController {


    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    @ModelAttribute("role")
    public Role get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return roleService.queryById(id);
        } else {
            return new Role();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView() {
        return "console/role/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list() throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Role> list = roleService.listAll();
        result.setTotal(list.size());
        result.setRows(list);

        return result;
    }


    @RequestMapping(value = "/listByUser.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult listByUser(Long uid) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Role> list = roleService.listByUser(uid);
        result.setTotal(list.size());
        result.setRows(list);

        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Role role, Model model) throws Exception {
        model.addAttribute("role", role);
        return "console/role/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Role role) {

        EUResult result = new EUResult();
        try {
            if (roleService.checkCode(role)) {
                result = EUResult.build(EUResult.FAIL, "角色编码已存在，请重新输入！");
            } else {
                if (role.getId() != null && role.getId() > 0) {
                    roleService.update(role);
                    //清除权限系统的缓存
                    ShiroUtils.clearAllCachedAuthorizationInfo();
                } else {
                    roleService.insert(role);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("角色信息保存成功！");
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
                    msg = msg + roleService.deleteOnCheck(id) + "<br> ";
                }
                result.setMsg(msg);
                //删除角色需要清除权限系统的缓存
                ShiroUtils.clearAllCachedAuthorizationInfo();
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除角色时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的角色！");
        }
        return result;
    }


    @RequestMapping(value = "/updatePerm/{rid}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult updatePerm(@PathVariable("rid") Long rid, @RequestBody List<Long> pids) {
        EUResult result = new EUResult();
        if(pids == null || pids.isEmpty()){
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择授予该角色的权限！");
        }else{
            try {
                roleService.updatePerms(rid, pids);
                result.setStatus(EUResult.OK);
                result.setMsg("角色授权成功！");
                //更新角色权限需要刷新shiro相关的登录信息缓存
                ShiroUtils.clearAllCachedAuthorizationInfo();
            }catch (Exception ex){
                result.setStatus(EUResult.FAIL);
                result.setMsg("系统出现异常！<br>" + ex.getMessage());
            }
        }
        return result;
    }
}
