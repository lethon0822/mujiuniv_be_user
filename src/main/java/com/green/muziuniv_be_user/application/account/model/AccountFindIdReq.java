package com.green.muziuniv_be_user.application.account.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AccountFindIdReq {
    private String email;
    private String phone;
}
