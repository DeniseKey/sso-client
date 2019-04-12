package com.aiwm.sso.client.controller;

import com.aiwm.sso.client.common.HttpClientUtils;
import com.aiwm.sso.client.common.LoginHelper;
import com.aiwm.sso.client.data.service.SsoClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 移除用户会话
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginLogout",method = RequestMethod.GET)
    @ResponseBody
    public String removeSession(HttpServletRequest request){
        String sessionIdByCookie = LoginHelper.getSessionIdByCookie(request);
        if(sessionIdByCookie!=null){
            Object attribute = request.getSession().getAttribute(sessionIdByCookie);
            if(attribute!=null){
                //清空session会话
                request.getSession().invalidate();
                return "yes";
            }
        }
        return "no";
    }

    public static void main(String[] args) {
        String doGet = HttpClientUtils.doGet("http://localhost:8083/sso-client/loginLogout");
        System.out.println(doGet);
    }
}
