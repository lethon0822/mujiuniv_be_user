package com.green.muziuniv_be_user.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    @Column(nullable = false,length = 100)
    private String address;

    @Column(nullable = false, length = 255)
    private String addDetail;

}
