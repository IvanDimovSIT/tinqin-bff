package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationRestExport authenticationRestExport;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            AuthenticateOutput authenticateOutput = authenticationRestExport.authenticate(authHeader);

            JwtToken jwtToken = jwtUtil.decodeHeader(authHeader);

            UserAuthority userAuthority = new UserAuthority(jwtToken.getRole().toUpperCase());

            LoggedUserDetails loggedUserDetails = new LoggedUserDetails(userAuthority, jwtToken.getId());

            Authentication authenticationToken = new JwtAuthentication(loggedUserDetails);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception ignored) {
        }

        filterChain.doFilter(request, response);
    }
}
