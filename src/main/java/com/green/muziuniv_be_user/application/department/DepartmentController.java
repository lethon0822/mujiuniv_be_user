package com.green.muziuniv_be_user.application.department;



import com.green.muziuniv_be_user.application.department.model.*;
import com.green.muziuniv_be_user.configuration.model.ResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {
    private final  DepartmentService departmentService;

    // 학과 관련

    // 학과 개설
    @PostMapping
    public ResultResponse<?> saveDepartment(@Valid @RequestBody DepartmentPostReq req){
        departmentService.saveDept(req);
        return new ResultResponse<>("학과 개설", null );
    }

    @GetMapping("/list")
    public ResultResponse<?> getDeptName(){
        List<DeptNameList> result = departmentService.deptName();
        return new ResultResponse<>("학과명", result);
    }

    // 학과 조회
    @GetMapping("/{id}")
    public ResultResponse<?> findDepartment(@ModelAttribute DepartmentGetReq req){
        log.info("알이큐:{}",req);
        if (req.getStatus() == "null") {
            DepartmentGetReq req2 = new DepartmentGetReq(null, req.getKeyword());
        }
        //List<DepartmentGetRes> result = departmentService.findAllDepartment(req);
        return new ResultResponse("학과 개설", null );
    }

    // 학과 소속 교수진 조회
    @GetMapping("/head")
    public ResultResponse<?> findDeptHead(@RequestParam("dept_id")int deptId){
        log.info("아이디{}", deptId);
        //List<DepartmentHeadName> result = departmentService.findDeptHead(deptId);
        return new ResultResponse("학과 개설", null );
    }

    // 학과 정보 수정
    @PutMapping
    public ResultResponse<?> updateDept(@RequestBody DepartmentPutReq req){
        //departmentService.updateDept(req);
        return new ResultResponse("학과 개설", null );
    }

    // 학과 상태 수정
    @PatchMapping
    public ResultResponse<?> updateDeptStatus(@RequestParam("id") int deptId){
        DepartmentPatchReq req = new DepartmentPatchReq(deptId);
        //departmentService.updateStatus(req);
        return new ResultResponse("학과 개설", null );
    }



}
