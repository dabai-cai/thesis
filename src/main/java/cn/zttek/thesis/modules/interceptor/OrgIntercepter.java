package cn.zttek.thesis.modules.interceptor;

import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @描述: 拦截后台的请求，判断是否有设置组织机构
 * @作者: Pengo.Wen
 * @日期: 2016-08-30 15:05
 * @版本: v1.0
 */
public class OrgIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().endsWith(".html") || request.getRequestURI().endsWith(".jsp")){
            return true;
        }

        HttpSession session = request.getSession(false);
        if(session != null){
            if(session.getAttribute("currentOrg") == null){
                throw new Exception("请重新登录后选择相应的组织机构！");
            }
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
