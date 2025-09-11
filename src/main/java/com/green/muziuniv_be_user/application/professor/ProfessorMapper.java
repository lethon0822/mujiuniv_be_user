package com.green.muziuniv_be_user.application.professor;

import com.green.muziuniv_be_user.application.professor.model.ProfessorBulkReq;
import com.green.muziuniv_be_user.application.professor.model.ProfessorDeptRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfessorMapper {
    List<ProfessorDeptRes> findProfessorsWithDept(ProfessorBulkReq req);
}
