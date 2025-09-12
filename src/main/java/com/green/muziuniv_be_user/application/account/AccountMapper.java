package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.AccountFindIdReq;
import com.green.muziuniv_be_user.application.account.model.AccountFindIdRes;
import com.green.muziuniv_be_user.application.account.model.AccountLoginReq;
import com.green.muziuniv_be_user.application.account.model.AccountLoginRes;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PrivacyGetRes;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PrivacyPutReq;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PwdPutReq;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AccountMapper {
    AccountLoginRes findByUserInfo(AccountLoginReq req);

    AccountFindIdRes findIdByEmailAndPhone(AccountFindIdReq req);

    String findDeptCodeByUserId(int userId);

    PrivacyGetRes selectMyPrivacy(int userId);

    int updateMyPrivacy(PrivacyPutReq req);

    int updateMyPwd(PwdPutReq req);
}