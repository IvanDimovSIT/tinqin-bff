package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.authentication.api.model.enums.UserRole;
import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

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
