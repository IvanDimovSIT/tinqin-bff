package com.tinqinacademy.bff.rest.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;



public class UserAuthority implements GrantedAuthority {
    private String authority;

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
