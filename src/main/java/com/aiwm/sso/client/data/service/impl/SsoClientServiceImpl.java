package com.aiwm.sso.client.data.service.impl;

import com.aiwm.sso.client.common.BaseCodeRes;
import com.aiwm.sso.client.common.LogMessage;
import com.aiwm.sso.client.common.Result;
import com.aiwm.sso.client.common.ResultHandler;
import com.aiwm.sso.client.data.mapper.NfCustomerAccountMapper;
import com.aiwm.sso.client.data.model.NfCustomerAccount;
import com.aiwm.sso.client.data.service.SsoClientService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by fengmc on 2019/3/27
 */
@Service
@Log4j2
public class SsoClientServiceImpl implements SsoClientService {

    @Resource
    private NfCustomerAccountMapper nfCustomerAccountMapper;

    @Override
    public Result checkToken(String accountToken) {
        LogMessage logMessage = new LogMessage(this.getClass().getSimpleName(), "checkToken").success();
        logMessage.append("accountToken",accountToken);
        Result result = null;
        try {
            result = new Result<>();
            if(StringUtils.isBlank(accountToken)){
                result=  ResultHandler.result(null, BaseCodeRes.MISSING_PARAM.getCode(), BaseCodeRes.MISSING_PARAM.getDesc());
                logMessage.append("checkToken parameter are missing: ",result);
            }
            NfCustomerAccount nfCustomerAccount = nfCustomerAccountMapper.checkToken(accountToken);
            if(nfCustomerAccount!=null){
                result= ResultHandler.result(null, BaseCodeRes.CHECK_USER.getCode(), BaseCodeRes.CHECK_USER.getDesc());
                logMessage.append("checkToken user Aiready exist: ",result);
            }else {
                result= ResultHandler.result(nfCustomerAccount, BaseCodeRes.SUCCESS.getCode(), BaseCodeRes.SUCCESS.getDesc());
                logMessage.append("checkToken success: ",result);
            }
        } catch (Exception e) {
            log.error("checkToken exception: ",e);
        }
        log.info(logMessage);
        return result;
    }
}
