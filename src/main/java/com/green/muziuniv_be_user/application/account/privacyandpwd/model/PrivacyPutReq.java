package com.green.muziuniv_be_user.application.account.privacyandpwd.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrivacyPutReq {
    private long userId;
    private String address;
    private String phone;
    private String email;
    private String addDetail;
    private String postcode;
}