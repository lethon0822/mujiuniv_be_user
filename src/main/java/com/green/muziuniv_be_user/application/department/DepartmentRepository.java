package com.green.muziuniv_be_user.application.department;


import com.green.muziuniv_be_user.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

//<연결할 엔터티, PK 타입>
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
