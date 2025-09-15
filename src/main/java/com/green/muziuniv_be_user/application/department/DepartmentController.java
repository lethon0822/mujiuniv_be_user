package com.green.muziuniv_be_user.application.department;



import com.green.muziuniv_be_user.application.department.model.*;
import com.green.muziuniv_be_user.configuration.model.ResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    // 필터바에서 사용하는 학과명을 들고오는 메소드
    @GetMapping("/list")
    public ResultResponse<?> getDeptName(){
        List<DeptNameList> result = departmentService.deptName();
        return new ResultResponse<>("학과명", result);
    }

    // 학과 조회
    @GetMapping
    public ResultResponse<?> findDepartment(@ModelAttribute DepartmentGetReq req){
        List<DepartmentGetRes> result = departmentService.findAllDepartment(req);
        return new ResultResponse<>("학과 개설", result );
    }

    // 학과 소속 교수진 조회
    @GetMapping("/head")
    public ResultResponse<?> findDeptHead(@RequestParam("dept_id")int deptId){
        log.info("아이디{}", deptId);
        List<DepartmentHeadName> result = departmentService.findDeptHead(deptId);
        return new ResultResponse("학과장 목록", result);
    }

    // 학과 코드 중복 조회
    @GetMapping("/code")
    public ResultResponse<?> findDuplicationDeptCode(@RequestParam("dept_code")String deptCode){
        int result = departmentService.findDeptCode(deptCode);
        return new ResultResponse<>("코드 확인", result);
    }

    // 학과 정보 수정
    @PutMapping
    public ResponseEntity<?> updateDept(@Valid @RequestBody DepartmentPutReq req){
        return departmentService.updateDept(req);
    }

    // 학과 상태 수정
    @PatchMapping
    public ResponseEntity<?> updateDeptStatus(@RequestParam("id") Long deptId){
        String result = departmentService.patchDeptStatus(deptId);
        return ResponseEntity.ok(result);
    }



}
