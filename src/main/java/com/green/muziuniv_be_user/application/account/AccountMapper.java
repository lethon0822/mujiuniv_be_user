package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.AccountFindIdReq;
import com.green.muziuniv_be_user.application.account.model.AccountFindIdRes;
import com.green.muziuniv_be_user.application.account.model.AccountLoginReq;
import com.green.muziuniv_be_user.application.account.model.AccountLoginRes;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AccountMapper {
    AccountLoginRes findByLoginId(AccountLoginReq req);

    AccountFindIdRes findIdByEmailAndPhone(AccountFindIdReq req);

    String findDeptCodeByUserId(int userId);
}