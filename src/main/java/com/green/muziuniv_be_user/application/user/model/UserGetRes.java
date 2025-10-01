package com.green.muziuniv_be_user.application.user.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGetRes {
    private long userId;
    private String loginId;
    private String userName;
    private String deptName;
    private String status;
    private String birthDate;
    private String email;
    private String postcode;
    private String address;
    private String addDetail;
    private String phone;
    private String userPic;

    //학생용
    private int grade;
    private String entDate;
    private int semester;

    //교수용
    private String hireDate;

}
