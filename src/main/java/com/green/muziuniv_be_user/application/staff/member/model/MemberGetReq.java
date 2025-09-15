package com.green.muziuniv_be_user.application.staff.member.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberGetReq {
    private String userRole;        // student | professor
    private Integer deptId;
    private Integer grade;          // 학생 전용
    private String status;          // 공통
    private String gender;          // 교수 전용 (user.gender 기준이면 학생도 사용 가능)
    private String q;
    private String searchBy;        // all | name | loginId | email
}


