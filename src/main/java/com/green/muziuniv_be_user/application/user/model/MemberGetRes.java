package com.green.muziuniv_be_user.application.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberGetRes {
    private int loginId;
    private String username;
    private String address;
    private String email;
    private String phone;
    private String deptName;
    private String status;
    private int grade;
}
