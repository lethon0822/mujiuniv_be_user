package com.green.muziuniv_be_user.entity.professor;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorIds implements Serializable {
    private Long userId;
    private Long deptId;
}
