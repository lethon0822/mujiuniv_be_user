package com.green.muziuniv_be_user.entity.staff;

import com.green.muziuniv_be_user.entity.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Staff {

    @Id
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;

    @Column(nullable = false)
    private LocalDate hireDate;


}
