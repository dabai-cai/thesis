package cn.zttek.thesis.modules.web.arch;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.common.utils.Md5Utils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Title;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.model.UserInfo;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.shiro.ShiroUtils;
import cn.zttek.thesis.utils.RequestUtil;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @描述: 档案管理控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-31 14:36
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/arch")
public class ArchController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TitleService titleService;

    @ModelAttribute("user")
    public User get(@RequestParam(required = false) Long id) throws Exception{
        if(id != null && id > 0){
            User temp = userService.getDetail(id);
            if(temp.getInfo() == null){
                temp.setInfo(new UserInfo());
            }
            return temp;
        }else{
            User temp = new User();
            temp.setInfo(new UserInfo());
            return temp;
        }
    }

    @RequestMapping(value = "/list-{type}", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model, @PathVariable String type) throws Exception{
        model.addAttribute("type", type);
        return "console/arch/list";
    }

    @RequestMapping(value = "/list-{type}.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, String keywords, @PathVariable String type) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        UserType userType = "admin".equals(type)? UserType.ADMIN : "teacher".equals(type) ? UserType.TEACHER : UserType.STUDENT;
        Org org = ThesisParam.getCurrentOrg();
        PageInfo<User> pageInfo = userService.listDetailByPage(page, rows, keywords, userType, org.getId());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = {"/add-{userType}", "/edit-{userType}"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute User user, Model model, @PathVariable String userType) throws Exception {
        if("teacher".equals(userType)){
            List<Title> titles = titleService.listAll();
            model.addAttribute("titles", titles);
        }
        model.addAttribute("user", user);
        model.addAttribute("type", userType);
        return "console/arch/edit";
    }

    @RequestMapping(value = "/edit-{userType}", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute User user, String pwd, String rids, @PathVariable String userType, HttpServletRequest request) {
        EUResult result = new EUResult();
        try {
            if (userService.checkAccount(user)) {
                result = EUResult.build(EUResult.FAIL, "用户账号已存在，请重新输入！");
            } else {
                UserInfo info = RequestUtil.getParamObject(request, UserInfo.class);
                BeanUtils.copyProperties(info, user.getInfo(), new String[]{"uid", "cdate", "mdate", "valid"});
                Org org = ThesisParam.getCurrentOrg();
                user.setOrgid(org.getId());
                Long[] ridArry = CommonUtils.getIdsArray(rids);
                if (user.getId() != null && user.getId() > 0) {
                    if(StringUtils.isNotEmpty(pwd)){
                        user.setPassword(new Md5Utils().getkeyBeanofStr(pwd));
                    }
                    userService.updateUser(user, ridArry);
                    ShiroUtils.clearTheUserCachedAuthenticationInfo(user);
                } else {
                    if(StringUtils.isNotEmpty(pwd)){
                        user.setPassword(new Md5Utils().getkeyBeanofStr(user.getAccount()));
                    }else{
                        user.setPassword(new Md5Utils().getkeyBeanofStr(pwd));
                    }
                    if("admin".equals(userType)) user.setType(UserType.ADMIN);
                    else if("teacher".equals(userType)) user.setType(UserType.TEACHER);
                    else user.setType(UserType.STUDENT);
                    userService.saveUser(user, ridArry);
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
                String msg = "";
                for (Long id : idsArry) {
                    msg = msg + userService.deleteOnCheck(id) + "<br>";
                }
                result.setStatus(EUResult.OK);
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

    @RequestMapping(value = {"/batch-{userType}"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String batch(Model model, @PathVariable String userType)throws Exception{
        model.addAttribute("type", userType);
        return "console/arch/batch";
    }

    @RequestMapping(value = {"/batch-{userType}"}, produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult batch(@PathVariable String userType, Long[] rids, String data)throws Exception{
        EUResult result;
        UserType type = "teacher".equals(userType) ? UserType.TEACHER : UserType.STUDENT;
        Org org = ThesisParam.getCurrentOrg();
        List<String> msgs = new ArrayList<>(50);
        List<User> users = preHandle(data, type, org, msgs);
        if(!msgs.isEmpty()){
            result = EUResult.build(EUResult.FAIL, "数据格式出错，请检查！", msgs);
        }else{
            msgs = userService.batchAddUser(users, rids);
            result = EUResult.build(EUResult.OK, "批量用户数据保存成功！", msgs);
        }
        return result;
    }

    /**
     * 对提交的批数据进行预处理
     * @param data
     * @return
     */
    private List<User> preHandle(String data, UserType type, Org org, List<String> msgs) throws Exception {
        Map<String, User> userMap = new HashMap<>(100);
        List<User> users = new ArrayList<>(100);

        Map<String, Title> titleMap = titleService.getTitleMap();

        String[] rows = data.trim().split("[\n\r]+");
        int fieldCount = type == UserType.TEACHER ? 3 : 5;

        for (int i = 0; i < rows.length; i++) {
            String[] info = rows[i].trim().split("[ \t]+");
            if (info.length != fieldCount) {
                msgs.add("第" + (i + 1) + "行的用户信息【" + rows[i] + "】格式不正确!");
            } else if (!Pattern.matches("[\\d]{4,20}", info[0])) {
                msgs.add("第" + (i + 1) + "行的用户账号【" + info[0] + "】格式不正确，请输入4-20位的数字!");
            } else if(type == UserType.TEACHER && !titleMap.containsKey(info[2])){
                msgs.add("第" + (i + 1) + "行的职称【" + info[2] + "】不存在，请先联系超级管理员添加相应的职称!");
            }else{
                if (userMap.containsKey(info[0].trim())) {
                    msgs.add("第" + (i + 1) + "行的用户账号【" + info[0] + "】在列表中重复，请检查!");
                } else {
                    User user = new User();
                    user.setAccount(info[0].trim());
                    user.setUsername(info[1].trim());
                    user.setOrgid(org.getId());
                    user.setType(type);
                    user.setPassword(new Md5Utils().getkeyBeanofStr(user.getAccount()));
                    UserInfo uInfo = new UserInfo();
                    if(type == UserType.TEACHER){
                        uInfo.setTid(titleMap.get(info[2]).getId());
                    }else if(type == UserType.STUDENT){
                        uInfo.setMajor(info[2]);
                        uInfo.setGrade(info[3]);
                        uInfo.setClazz(info[4]);
                    }

                    user.setInfo(uInfo);
                    userMap.put(info[0].trim(), user);
                    users.add(user);
                }
            }
        }
        return users;
    }

    @RequestMapping(value = {"/view"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String view(@ModelAttribute User user, Model model) throws Exception {
        if(user.getType() == UserType.TEACHER){
            user.getInfo().setTitle(TitleHolder.getTitle(user.getInfo().getTid()));
        }
        model.addAttribute("type", user.getType() == UserType.TEACHER ? "teacher" : "student");
        model.addAttribute("user", user);
        return "console/arch/view";
    }
}
