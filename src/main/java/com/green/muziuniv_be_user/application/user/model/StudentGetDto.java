package com.green.muziuniv_be_user.application.user.model;


import lombok.Getter;

@Getter
public class StudentGetDto {
    private String userId;
    private String loginId;
    private String userName;
    private int grade;
    private String deptName;
}
