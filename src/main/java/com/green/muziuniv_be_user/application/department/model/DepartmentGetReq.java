package com.green.muziuniv_be_user.application.department.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DepartmentGetReq {
    private String status;
    private String keyword;
}
