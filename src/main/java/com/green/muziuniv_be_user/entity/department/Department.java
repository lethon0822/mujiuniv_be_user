package com.green.muziuniv_be_user.entity.department;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    private Long headProfId;

    @Column(nullable = false, length = 20)
    private String deptName;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 50)
    private String deptOffice;

    @Column(nullable = false, length = 50)
    private String deptTel;

    @Column(nullable = false)
    private int deptMaxStd;

    @Column(nullable = false, length = 50, unique = true)
    private String deptCode;
}

