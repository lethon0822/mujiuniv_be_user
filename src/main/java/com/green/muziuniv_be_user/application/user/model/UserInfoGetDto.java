package com.green.muziuniv_be_user.application.user.model;


import lombok.Getter;

@Getter
public class UserInfoGetDto {
    private Long userId;
    private String userName;
    private String deptName;
    private Integer status;
}
