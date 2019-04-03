package com.aiwm.sso.client.data.mapper;

import com.aiwm.sso.client.data.model.NfCustomerAccount;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface NfCustomerAccountMapper extends Mapper<NfCustomerAccount> {
    /**
     * 查询token
     * @param accountToken
     * @return
     */
    NfCustomerAccount checkToken(@Param("accountToken") String accountToken);
}