package com.green.muziuniv_be_user.entity.professor;

import com.green.muziuniv_be_user.entity.department.Department;
import com.green.muziuniv_be_user.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Professor {
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
    private LocalDate hireDate;

    @Column(nullable = false, length = 20)
    private String status;

    //save 시점에 한번 시행되는 메소드
    @PrePersist
    public void prePersist() { //default 처리는 무조건 이걸로.
        if (user == null) {
            this.status = "재직";
        }
    }
}
