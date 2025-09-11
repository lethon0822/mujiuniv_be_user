package com.green.muziuniv_be_user.application.professor;

import com.green.muziuniv_be_user.application.professor.model.ProfessorBulkReq;
import com.green.muziuniv_be_user.application.professor.model.ProfessorDeptRes;
import com.green.muziuniv_be_user.common.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RequestMapping("/professor")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {
    private final ProfessorService professorService;


    // 강의 조회 용
    // 요청: 승인된 강의들의 교수 ID 리스트
    // 응답: 각 교수의 이름과 소속 학과 정보
    @PostMapping("/bulk")
    public ResultResponse<List<ProfessorDeptRes>> findProfessors(@RequestBody ProfessorBulkReq req){
        log.info("요청 교수 ID 리스트: {}", req.getProfessorIds());
        List<ProfessorDeptRes> result = professorService.findProfessorsWithDept(req);
        return new ResultResponse<>("교수 + 학과 조회 성공", result);
    }
}
