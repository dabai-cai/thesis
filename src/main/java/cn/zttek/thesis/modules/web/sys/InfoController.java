package cn.zttek.thesis.modules.web.sys;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.Md5Utils;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Title;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.model.UserInfo;
import cn.zttek.thesis.modules.service.TitleService;
import cn.zttek.thesis.modules.service.UserService;
import cn.zttek.thesis.shiro.ShiroUtils;
import cn.zttek.thesis.utils.RequestUtil;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @描述: 修改个人资料相关控制器
 * @作者: Pengo.Wen
 * @日期: 2016-10-09 20:07
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/info")
public class InfoController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private TitleService titleService;

    @RequestMapping(value = {"/edit"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit( Model model) throws Exception {
        User currentUser = ThesisParam.getCurrentUser();
        model.addAttribute("user", currentUser);
        String type;
        switch (currentUser.getType()){
            case ADMIN: type = "admin"; break;
            case TEACHER: type = "teacher"; break;
            case STUDENT: type = "student"; break;
            default: type = "admin";
        }
        model.addAttribute("type", type);
        if(currentUser.getType() == UserType.TEACHER){
            List<Title> titles = titleService.listAll();
            model.addAttribute("titles", titles);
        }
        return "console/info/edit";
    }

    @RequestMapping(value = "/edit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(String username, String pwd, HttpServletRequest request) {
        User currentUser = ThesisParam.getCurrentUser();
        EUResult result = new EUResult();
        try {
            UserInfo info = RequestUtil.getParamObject(request, UserInfo.class);
            BeanUtils.copyProperties(info, currentUser.getInfo(), new String[]{"id", "uid", "orgid", "cdate", "mdate", "valid"});
            currentUser.setUsername(username);
            if(StringUtils.isNotEmpty(pwd)){
                currentUser.setPassword(new Md5Utils().getkeyBeanofStr(pwd));
            }
            userService.updateUser(currentUser, null);
            ShiroUtils.clearTheUserCachedAuthenticationInfo(currentUser);
            result.setStatus(EUResult.OK);
            result.setMsg("个人资料修改成功！");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }

        return result;
    }

}
