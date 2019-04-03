package com.aiwm.sso.client.data.service;

import com.aiwm.sso.client.common.Result;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fengmc on 2019/3/27
 */
public interface SsoClientService {
    /**
     * 查询token
     * @param accountToken
     * @return
     */
    Result checkToken(@Param("accountToken") String accountToken);
}
