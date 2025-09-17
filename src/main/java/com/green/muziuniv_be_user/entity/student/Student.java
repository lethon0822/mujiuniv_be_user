package com.green.muziuniv_be_user.entity.student;

import com.green.muziuniv_be_user.entity.department.Department;
import com.green.muziuniv_be_user.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Student {

    @Id
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private int grade = 1;

    @Column(nullable = false)
    private int semester = 1;

    @Column(nullable = false)
    private LocalDate entDate;

    @Column
    private LocalDate graduDate;

    @Column(nullable = false, length = 10)
    private String status = "재학";

    @Column(nullable = false, length = 10)
    private int majCredit = 0;

    @Column(nullable = false, length = 10)
    private int geCredit = 0;

 }
