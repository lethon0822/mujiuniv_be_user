package com.green.muziuniv_be_user.application.user.Repository;

import com.green.muziuniv_be_user.entity.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
