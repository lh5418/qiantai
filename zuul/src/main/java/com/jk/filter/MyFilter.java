package com.jk.filter;

import com.jk.pojo.UserBean;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: springcloud-user
 * @description:
 * @author: 刘海
 * @create: 2021-01-10 19:23
 */
@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = request.getRequestURI();
        if (uri.contains("/user/login")){
            return false;
        }
        if (uri.contains("/user/gainMsgCode")){
            return false;
        }
        if (uri.contains("/user/login2")){
            return false;
        }
        if (uri.contains("/api-a/js")){
            return false;
        }


        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("走过滤器‐‐‐‐‐‐‐‐");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("user");
        //System.out.println("用户名："+userBean.getName());
        if(userBean!=null){
            ctx.setSendZuulResponse(true);
            System.out.println("请求不拦截");
        }else {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
            System.out.println("请求被拦截！！");
            HttpServletResponse response = ctx.getResponse();
            try {
                request.getRequestDispatcher("/user/toLogin").forward(request, response);
            }catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


}
