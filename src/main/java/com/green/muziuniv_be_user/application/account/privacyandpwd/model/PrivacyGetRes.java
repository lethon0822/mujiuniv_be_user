package com.green.muziuniv_be_user.application.account.privacyandpwd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivacyGetRes {
    private long loginId;
    private String userName;
    private String address;
    private String phone;
    private String email;
    private String addDetail;
    private String postcode;
}