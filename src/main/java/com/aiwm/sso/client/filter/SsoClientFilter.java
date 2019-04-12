package com.aiwm.sso.client.filter;

import com.aiwm.sso.client.common.Conf;
import com.aiwm.sso.client.common.HttpClientUtils;
import com.aiwm.sso.client.common.LoginHelper;
import com.aiwm.sso.client.data.service.SsoClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengmc on 2019/3/27
 */

public class SsoClientFilter implements Filter {
    @Value("${sso.service.url}")
    private String ssoService;
    @Value("${sso.client.url}")
    private String redirect_url;
    @Value("${sso.token.url}")
    private String token_url;
    @Value("${sso.logout.url}")
    private String logout_url;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("SsoClientFilter init 初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=utf-8");

        //获取项目访问路径
        String link = req.getRequestURL().toString();
        String servletPath = req.getServletPath();
        //注销
        if(Conf.LOGOUT.equals(servletPath)){
            res.sendRedirect(logout_url);
            return;
        }
        String sessionIdByCookie = LoginHelper.getSessionIdByCookie(req);
        if(StringUtils.isBlank(sessionIdByCookie)){

            String toSsoServie=ssoService+"?"+redirect_url+"="+link;
            res.sendRedirect(toSsoServie);
            return;
        }else {
            //校验token
            String token = req.getParameter("token");
            if(token!=null){
                Map<String, String> hashMap = new HashMap<>();
                //传token和cookie值
                hashMap.put("token",token);
                hashMap.put("userKey",sessionIdByCookie);
                String doGet = HttpClientUtils.doGet(token_url, hashMap);
                if(doGet.equals("no")){
                    String toSsoServie=ssoService+"?"+redirect_url+"="+link;
                    res.sendRedirect(toSsoServie);
                    return;
                }
            }else {
                String toSsoServie=ssoService+"?"+redirect_url+"="+link;
                res.sendRedirect(toSsoServie);
                return;
            }
        }

        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }

}
