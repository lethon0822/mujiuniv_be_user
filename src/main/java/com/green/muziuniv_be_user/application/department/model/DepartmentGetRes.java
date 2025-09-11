package com.green.muziuniv_be_user.application.department.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentGetRes {
    private int deptId;
    private String deptName;
    private String status;
    private int headProfId;
    private String deptOffice;
    private String deptTel;
    private String deptMaxStd;
    private String deptPeople;
    private String deptCode;
    private String userName;
}
