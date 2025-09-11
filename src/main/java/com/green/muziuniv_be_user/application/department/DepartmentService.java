package com.green.muziuniv_be_user.application.department;



import com.green.muziuniv_be_user.application.department.model.DepartmentPostReq;
import com.green.muziuniv_be_user.entity.department.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private DepartmentRepository departmentRepository;
    //private DepartmentMapper departmentMapper;



    //TODO 예외처리
    // 예외 처리 메소드 따로 만들고 호출 saveDept, updateDept

    public void saveDept(DepartmentPostReq req){
        //Department dept =

    }



}
