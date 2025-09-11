package com.green.muziuniv_be_user.application.professor.model;

import lombok.Getter;

import java.util.List;

@Getter
public class ProfessorBulkReq {
    private List<Long> professorIds;
}
