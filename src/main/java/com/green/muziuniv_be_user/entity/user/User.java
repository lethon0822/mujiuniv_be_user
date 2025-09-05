package com.green.muziuniv_be_user.entity.user;

import com.green.muziuniv_be_user.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 10)
    private String loginId;

    @Column(nullable = false,length = 10)
    private String userRole;

    @Column(nullable = false,length = 25)
    private String password;

    @Column(nullable = false,length = 10)
    private String userName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false,length = 1)
    private String gender;

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String phone;

    @Column(length = 50)
    private String userPic;

    @Column(length = 50)
    private String account;

}
