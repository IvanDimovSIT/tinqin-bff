package com.tinqinacademy.bff.rest.security;

import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@ToString
@Getter
@Setter
public class JwtAuthentication extends AbstractAuthenticationToken {
    private LoggedUserDetails loggedUserDetails;

    public JwtAuthentication(LoggedUserDetails loggedUserDetails) {
        super(loggedUserDetails.getAuthorities());
        this.loggedUserDetails = loggedUserDetails;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return loggedUserDetails;
    }
}
