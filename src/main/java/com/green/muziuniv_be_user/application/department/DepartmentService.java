package com.green.muziuniv_be_user.application.department;



import com.green.muziuniv_be_user.application.department.model.DepartmentPostReq;
import com.green.muziuniv_be_user.application.department.model.DeptNameList;
import com.green.muziuniv_be_user.entity.department.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;



    //TODO 예외처리
    // 예외 처리 메소드 따로 만들고 호출 saveDept, updateDept

    public void saveDept(DepartmentPostReq req){
        //Department dept =

    }

    public List<DeptNameList> deptName(){
        return departmentMapper.findDeptName();
    }

}
