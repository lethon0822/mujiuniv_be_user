package com.green.muziuniv_be_user.application.department;



import com.green.muziuniv_be_user.application.department.model.*;
import com.green.muziuniv_be_user.entity.department.Department;
import com.green.muziuniv_be_user.entity.professor.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    //통신용
    public List<DeptNameList> deptName(){
        return departmentMapper.findDeptName();
    }

    //저장
    public void saveDept(DepartmentPostReq req){
        Department dept = Department.builder()
                .deptCode(req.getDeptCode())
                .deptTel(req.getDeptTel())
                .deptOffice(req.getDeptOffice())
                .deptName(req.getDeptName())
                .deptMaxStd(req.getDeptMaxStd())
                .build();

        // TODO 예외처리(unique)
            departmentRepository.save(dept);

    }

    //데이터 가져오기
    public List<DepartmentGetRes> findAllDepartment(DepartmentGetReq req){
        return departmentMapper.findAll(req);
    }

    // 학과 코드 생성
    public String createDeptCode(){
        Random random = new Random();
        String resultCode;

        while(true){
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                // 'A'의 아스키코드는 65
                char c = (char) ('A' + random.nextInt(26)); // 0~25 → A~Z
                code.append(c);
            }
            String result = code.toString();
            int checkDuplicateCode = departmentMapper.findDeptCode(result);
            if(checkDuplicateCode == 0){
                resultCode = result;
                break;
            }
        }
        return resultCode;
    }

    // 학과 정보 수정

    public ResponseEntity<?> updateDept(DepartmentPutReq req){
        Department dept = departmentRepository.findById(req.getDeptId())
                .orElseThrow(() -> new RuntimeException("학과가 존재하지 않습니다"));

        Professor professor  = Professor.builder()
                .userId(req.getHeadProfId())
                .build();

        dept.setDeptId(req.getDeptId());
        dept.setDeptCode(req.getDeptCode());
        dept.setDeptName(req.getDeptName());
        dept.setDeptTel(req.getDeptTel());
        dept.setDeptOffice(req.getDeptOffice());
        dept.setDeptMaxStd(req.getDeptMaxStd());
        dept.setHeadProfId(professor);
        //TODO 유니크 예외처리


        departmentRepository.save(dept);
        return null;
    }

    //학과 교수 목록
    public List<DepartmentHeadName> findDeptHead(int deptId){
        return departmentMapper.findUserByDeptId(deptId);
    }

    //학과 상태 수정
    public String patchDeptStatus(Long deptId){
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("학과가 존재하지 않습니다"));

        if(dept.getStatus().equals("0")){
            return "이미 폐지된 학과입니다.";
        }
        dept.setStatus("0");
        departmentRepository.save(dept);
        return null;
    }

}
