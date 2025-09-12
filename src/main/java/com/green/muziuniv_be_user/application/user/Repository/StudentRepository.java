package com.green.muziuniv_be_user.application.user.Repository;

import com.green.muziuniv_be_user.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
