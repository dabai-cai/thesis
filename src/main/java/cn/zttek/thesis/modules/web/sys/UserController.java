package cn.zttek.thesis.modules.web.sys;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUComboResult;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.common.utils.Md5Utils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.shiro.ShiroUtils;
import cn.zttek.thesis.utils.ThesisParam;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @描述:
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 17:51
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User get(@RequestParam(required = false, value = "id") Long id) throws Exception {
        if (id != null && id > 0) {
            return userService.queryById(id);
        } else {
            return new User();
        }
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String listView(Model model) {
        model.addAttribute("types", UserType.values());
        return "console/user/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, String keywords, UserType type){
        EUDataGridResult result = new EUDataGridResult();
        try {
            PageInfo<User> pageInfo = userService.listByPage(page, rows, keywords, type);
            result.setTotal(pageInfo.getTotal());
            result.setRows(pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = {"/add", "/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute User user, Model model) throws Exception {
        model.addAttribute("types", UserType.values());
        model.addAttribute("user", user);
        return "console/user/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute User user, String pwd) {

        EUResult result = new EUResult();
        try {
            if (userService.checkAccount(user)) {
                result = EUResult.build(EUResult.FAIL, "用户账号已存在，请重新输入！");
            } else {
                if (user.getId() != null && user.getId() > 0) {
                    if(StringUtils.isNotEmpty(pwd)){
                        user.setPassword(new Md5Utils().getkeyBeanofStr(pwd));
                    }
                    userService.update(user);
                    //DONE 更新用户的密码需要刷新shiro相关的登录信息缓存
                    ShiroUtils.clearTheUserCachedAuthenticationInfo(user);
                } else {
                    if(StringUtils.isNotEmpty(pwd)){
                        user.setPassword(new Md5Utils().getkeyBeanofStr(user.getAccount()));
                    }else{
                        user.setPassword(new Md5Utils().getkeyBeanofStr(pwd));
                    }
                    userService.insert(user);
                }
                result.setStatus(EUResult.OK);
                result.setMsg("用户信息保存成功！");
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
                    msg = msg + userService.deleteOnCheck(id) + "<br>";
                }
                result.setMsg(msg);
                //DONE 更新用户角色需要刷新shiro相关的登录信息缓存
                ShiroUtils.clearAllCachedAuthorizationInfo();
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg("删除用户时发生异常！" + e.getMessage());
            }
        } else {
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要删除的用户！");
        }
        return result;
    }


    @RequestMapping(value = "/updateRole/{uid}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult updateRole(@PathVariable("uid") Long uid, @RequestBody List<Long> rids) {
        EUResult result = new EUResult();
        if(rids == null || rids.isEmpty()){
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择授予该用户的角色！");
        }else{
            try {
                userService.updateRoles(uid, rids);
                result.setStatus(EUResult.OK);
                result.setMsg("用户授权成功！");
                //清除该用户的登录信息缓存
                ShiroUtils.clearAllCachedAuthorizationInfo();
            }catch (Exception ex){
                result.setStatus(EUResult.FAIL);
                result.setMsg("系统出现异常！<br>" + ex.getMessage());
            }
        }
        return result;
    }


    /*******************************以下为针对学生的查询：专业、年级、班级数据接口***********************************/
    @RequestMapping(value = "/listMajor.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUComboResult> listMajor(Long pid) throws Exception {
        Org org = ThesisParam.getCurrentOrg();
        List<String> majors = userService.getMajors(org.getId(), pid);
        return EUComboResult.createResults(majors);
    }
    @RequestMapping(value = "/listGradeByMajor.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUComboResult> listGradeByMajor(String major,Long pid) throws Exception {
        Org org = ThesisParam.getCurrentOrg();
        List<Integer> grades = userService.getGrade(org.getId(), pid, major);
        return EUComboResult.createResults(grades);
    }
    @RequestMapping(value = "/listClazzByMajorAndGrade.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUComboResult> listClazzByMajorAndGrade(String major, Integer grade, Long pid) throws Exception {
        Org org = ThesisParam.getCurrentOrg();
        List<String> clazzList = userService.getClazz(org.getId(), pid, major, grade);
        return EUComboResult.createResults(clazzList);
    }
}
