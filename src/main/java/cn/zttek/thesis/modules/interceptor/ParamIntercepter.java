package cn.zttek.thesis.modules.interceptor;

import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.utils.ThesisParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @描述: 拦截后台的请求，设置当前登录用户
 * @作者: Pengo.Wen
 * @日期: 2016-08-30 15:05
 * @版本: v1.0
 */
public class ParamIntercepter implements HandlerInterceptor {

    private Logger log = Logger.getLogger(ParamIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().endsWith(".html") || request.getRequestURI().endsWith(".jsp")){
            return true;
        }

        HttpSession session = request.getSession(false);
        if(session != null){
            if(session.getAttribute("currentUser") != null){
                ThesisParam.setCurrentUser((User)session.getAttribute("currentUser"));
                log.info("===>>session id: " + session.getId() + "\tIP: " + request.getRemoteAddr() + "\t current user: " + ThesisParam.getCurrentUser().getUsername());
            }

            if(session.getAttribute("currentOrg") != null){
                ThesisParam.setCurrentOrg((Org)session.getAttribute("currentOrg"));
            }

            if(session.getAttribute("currentProj") != null){
                ThesisParam.setCurrentProj((Project)session.getAttribute("currentProj"));
            }
        }else{
            throw new Exception("操作超时，请重新登录后操作！");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
