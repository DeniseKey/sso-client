package com.aiwm.sso.client.common;

import org.springframework.context.annotation.Configuration;

/**
 * Created by fengmc on 2019/3/28
 */
@Configuration
public class Conf {
   /* @Value("${sso.service.url}")
    private String ssoService;
    @Value("${sso.client.url}")
    private String redirect_url;*/
    public static final String SSOSERVICE="http://localhost:8082/sso-service/index";
    public static final String REDIRECT_URL="redirect_url";
    public static final String SSO_SESSIONID = "sso_sessionid";
}
