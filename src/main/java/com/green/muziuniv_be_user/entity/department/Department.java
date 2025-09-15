package com.green.muziuniv_be_user.entity.department;

import com.green.muziuniv_be_user.entity.professor.Professor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    @OneToOne
    @JoinColumn(name = "head_prof_id")
    private Professor headProfId;

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

    @Column(nullable = false, length = 10, unique = true)
    private String deptCode;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "1";
        }
    }

}

