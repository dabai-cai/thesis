package cn.zttek.thesis.modules.web;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.utils.Md5Utils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.OrgService;
import cn.zttek.thesis.modules.service.RoleService;
import cn.zttek.thesis.modules.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * @描述: 登录控制器
 * @作者: Pengo.Wen
 * @日期: 2016-08-29 15:39
 * @版本: v1.0
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 跳转到登陆页面
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/console/login.html", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) throws Exception {
        String loginPath = request.getParameter("loginPath");
        if (StringUtils.isEmpty(loginPath)) {
            loginPath = "/console/index";
        }

        User user = (User) request.getSession().getAttribute("currentUser");
        if (user != null) {
            return "redirect:" + loginPath;
        }

        model.addAttribute("loginPath", loginPath);
        return "console/login";
    }

    @RequestMapping(value = "/console/login.html", method = RequestMethod.POST)
    public String login(User user, String code, Model model, HttpSession session, RedirectAttributes redirectAttributes, String loginPath) throws Exception {
        //处理验证码
        /*if (!(code.equalsIgnoreCase(session.getAttribute("code").toString()))) {
            redirectAttributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/console/login.html?loginPath=" + loginPath;
        }*/
/*        User user  = new User();
        user.setAccount("admin");
        user.setPassword("admin");*/
        //使用shiro管理登录，会调用到MyRealm.doGetAuthenticationInfo(),可以在里面设置抛出各种异常
        try {
            user.setPassword(new Md5Utils().getkeyBeanofStr(user.getPassword()));
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getAccount(), user.getPassword()));
        } catch (Exception e) {
            //可以写跳转的处理异常的页面
            redirectAttributes.addFlashAttribute("message", "账号或者密码错误");
            return "redirect:/console/login.html?loginPath=" + loginPath;
        }

        //用shiro进行登陆逻辑，这里只需要取user对象放入session中。
        User currentUser = userService.getByAccount(user.getAccount());
        List<Role> roles = roleService.listByUser(currentUser.getId());

        //加载用户管理的机构
        if(roles.isEmpty()){
            if(currentUser.getType() != UserType.SUPER){
                redirectAttributes.addFlashAttribute("message", "该用户未设置角色，不能登录！");
                return "redirect:/console/login.html?loginPath=" + loginPath;
            }
        }else{
            if(currentUser.getType() != UserType.SUPER && (currentUser.getInfo() == null || currentUser.getOrgid() == null)){
                redirectAttributes.addFlashAttribute("message", "该用户未设置组织机构，不能登录！");
                return "redirect:/console/login.html?loginPath=" + loginPath;
            }
        }
        session.setAttribute("currentUser", currentUser);
        return "redirect:" + loginPath;
    }

    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpSession session) {
//        使用shiro 的 logout() ，它会自动将 HttpSession 给invalidate掉，
//        所以如果在下面这句代码之后再用到session的话，会抛出异常
//        org.apache.shiro.session.UnknownSessionException
//        SecurityUtils.getSubject().logout();
//        同样的，执行 HttpSession 的invalidate() 也会自动把shiro的会话给 stop 掉
        session.invalidate();
        return "redirect:/console/login.html";
    }

}
