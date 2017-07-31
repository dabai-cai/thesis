package cn.zttek.thesis.common.base;

import cn.zttek.thesis.common.converter.StringToTimeConverter;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.enums.UserType;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by ztcms on 2015/10/18.
 */
public class BaseController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : text.trim());
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        //String 转 Timestamp
        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                setValue(StringToTimeConverter.convert(value));
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Date date;
                try {
                    System.out.println("===日期转换==");
                    date = DateUtils.parseDate(text, new String[]{"yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss"});
                } catch (ParseException e) {
                    date = new Date();
                }
                setValue(date);
            }
        });

        binder.registerCustomEditor(UserType.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                int val = 0;
                try {
                    val = Integer.parseInt(text);
                }catch (Exception ex){

                }
                setValue(UserType.getById(val));
            }
        });
        binder.registerCustomEditor(TitleLevel.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                int val = 0;
                try {
                    val = Integer.parseInt(text);
                }catch (Exception ex){

                }
                setValue(TitleLevel.getById(val));
            }
        });

        binder.registerCustomEditor(DefenseStatus.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                int val = 0;
                try {
                    val = Integer.parseInt(text);
                }catch (Exception ex){

                }
                setValue(DefenseStatus.getById(val));
            }
        });
    }


    /**
     * 权限异常处理
     *
     * @param session
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    public String handleAuthorizationException(HttpSession session) {
        if(session.getAttribute("currentUser")==null){
            log.error("-->用户未登录");
        } else{
            log.error("-->该用户没有此权限");
        }
        return "console/error";
    }

    /**
     * 如果登录之后，没有等页面完全加载就注销，会报错。
     * @return
     */
    @ExceptionHandler(UnknownSessionException.class)
    public String handleUnknownSessionException(Exception e){
        log.error("-->" + e.getMessage());
        return "redirect:/index.html";
    }

    /**
     * 系统异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        log.error("-->系统发生异常", e);
        ModelAndView model = new ModelAndView();
        model.addObject("message", e.getMessage());
        model.setViewName("console/error");
        return model;
    }

    /**
     * 上传文件的路径
     *
     * @param request
     * @return
     * @throws MalformedURLException
     */
    protected String getDocRoot(HttpServletRequest request) throws MalformedURLException {
        String path = request.getServletContext().getRealPath("/") + "upload";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }
}
