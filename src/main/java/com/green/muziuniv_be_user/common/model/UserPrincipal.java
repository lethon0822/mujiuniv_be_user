package com.green.muziuniv_be_user.common.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class UserPrincipal implements UserDetails {
    private final JwtUser jwtUser;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(JwtUser jwtUser) {
        this.jwtUser = jwtUser ;

        this.authorities= Collections.singletonList(new SimpleGrantedAuthority(jwtUser.getRole()));

        //this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }


    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return null; }
}
