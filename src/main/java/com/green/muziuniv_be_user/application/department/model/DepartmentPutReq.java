package com.green.muziuniv_be_user.application.department.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentPutReq {
    private int deptId;
    private int headProfId;
    private String deptName;
    private String deptOffice;
    private String deptTel;
    private int deptMaxStd;
    private String deptCode;

}
