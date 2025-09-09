package com.green.muziuniv_be_user.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 10)
    private String loginId;

    @Column(nullable = false,length = 10)
    private String userRole;

    @Column(nullable = false,length = 225)
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

    @Column(nullable = false, unique = true, length = 13)
    private String phone;

    @Column(length = 50)
    private String userPic;


}
