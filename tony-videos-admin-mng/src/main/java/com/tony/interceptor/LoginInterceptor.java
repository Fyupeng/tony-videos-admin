package com.tony.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    //在springmvc配置文件中配置自动对该属性赋值
    private List<String> unCheckUrls;

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        //将获取到的URI字符串上下文根除去
        requestURI = requestURI.replaceAll(request.getContextPath(), "");
        //System.out.println(requestURI);
        //System.out.println(unCheckUrls);
        //如果用户访问的路径在没有检查的字符串unCheckUrls中，则不拦截，放行
        if(unCheckUrls.contains(requestURI)){
            return true;
        }

        //如果会话被用户手动remove或已过期，对其拦截并重定向到登录页面
        if(null == request.getSession().getAttribute("sessionUser")){
            response.sendRedirect(request.getContextPath() + "/users/login.action");
            //拦截，用户通过index转发到center暂时中断，等拦截放行
            return false;
        }
        //拦截处理完后，拦截放行，center转发页面放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("after");
    }
}
