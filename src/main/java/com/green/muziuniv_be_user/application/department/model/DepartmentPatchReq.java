package com.green.muziuniv_be_user.application.department.model;

import lombok.Getter;

@Getter

public class DepartmentPatchReq {
    private String status = "0";
    private int deptId;


    public DepartmentPatchReq(int deptId){
        this.deptId = deptId;
    }
}
