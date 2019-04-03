package com.aiwm.sso.client.filter;

import com.aiwm.sso.client.common.BaseCodeRes;
import com.aiwm.sso.client.common.LoginHelper;
import com.aiwm.sso.client.common.Result;
import com.aiwm.sso.client.data.service.SsoClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by fengmc on 2019/3/27
 */

public class SsoClientFilter implements Filter {
    @Value("${sso.service.url}")
    private String ssoService;
    @Value("${sso.client.url}")
    private String redirect_url;
  /*  private String ssoService;
    private String redirect_url;*/

    @Resource
    private SsoClientService ssoClientService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

     /*   ssoService= Conf.SSOSERVICE;
        redirect_url = Conf.REDIRECT_URL;*/
        System.out.println("SsoClientFilter init 初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=utf-8");

        //获取项目访问路径
        String link = req.getRequestURL().toString();

        String sessionIdByCookie = LoginHelper.getSessionIdByCookie(req);
        if(StringUtils.isBlank(sessionIdByCookie)){

            //创建cookie 并保存velue
            LoginHelper.setSessionIdInCookie(res, UUID.randomUUID().toString());
        }

        String sessionIdByCookie1 = LoginHelper.getSessionIdByCookie(req);

        if(sessionIdByCookie1!=null){
            Result resultTs = ssoClientService.checkToken(sessionIdByCookie1);
            if(resultTs.getCode().equals(BaseCodeRes.SUCCESS.getCode())){
                //表示库里不存在
                String toSsoServie=ssoService+"?"+redirect_url+"="+link;
                res.sendRedirect(toSsoServie);
                return;
            }
            req.getSession().setAttribute("isLogin",true);
            //其他情况一律放行
        }else {
            String toSsoServie=ssoService+"?"+redirect_url+"="+link;
            res.sendRedirect(toSsoServie);
            return;
        }

        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
