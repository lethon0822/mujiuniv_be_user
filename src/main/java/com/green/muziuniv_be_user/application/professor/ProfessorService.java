package com.green.muziuniv_be_user.application.professor;

import com.green.muziuniv_be_user.application.professor.model.ProfessorBulkReq;
import com.green.muziuniv_be_user.application.professor.model.ProfessorDeptRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    List<ProfessorDeptRes> findProfessorsWithDept(ProfessorBulkReq req){
        return professorMapper.findProfessorsWithDept(req);
    }

}
