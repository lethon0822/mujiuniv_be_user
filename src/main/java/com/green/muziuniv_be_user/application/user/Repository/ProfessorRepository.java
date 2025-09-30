package com.green.muziuniv_be_user.application.user.Repository;

import com.green.muziuniv_be_user.entity.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUserId(Long userId);
}
