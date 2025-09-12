package com.green.muziuniv_be_user.application.account.privacyandpwd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PwdPutReq {
    private String email;
    private String password;
}