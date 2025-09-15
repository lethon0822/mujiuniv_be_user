package com.green.muziuniv_be_user.application.department;

import com.green.muziuniv_be_user.application.department.model.DepartmentGetReq;
import com.green.muziuniv_be_user.application.department.model.DepartmentGetRes;
import com.green.muziuniv_be_user.application.department.model.DepartmentHeadName;
import com.green.muziuniv_be_user.application.department.model.DeptNameList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<DeptNameList> findDeptName();

    List<DepartmentGetRes> findAll(DepartmentGetReq req);

    int findDeptCode(String deptCode);

    List<DepartmentHeadName> findUserByDeptId(int deptId);
}
