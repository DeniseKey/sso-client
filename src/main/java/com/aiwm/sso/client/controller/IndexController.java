package com.aiwm.sso.client.controller;

import com.aiwm.sso.client.common.LoginHelper;
import com.aiwm.sso.client.data.service.SsoClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by fengmc on 2019/3/27
 */
@Controller
public class IndexController {

    @Resource
    private SsoClientService ssoClientService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){

        return "index";
    }
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String welcome(HttpServletRequest request){
        String sessionIdByCookie = LoginHelper.getSessionIdByCookie(request);
        if (sessionIdByCookie!=null){
            request.getSession().setAttribute(sessionIdByCookie,true);
        }
        return "index";
    }
    @RequestMapping(value = "/errorTo",method = RequestMethod.GET)
    public String error(){
        return "error";
    }
}
