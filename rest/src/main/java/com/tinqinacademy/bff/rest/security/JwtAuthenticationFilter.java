package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateInput;
import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try{
            AuthenticateInput input = AuthenticateInput.builder()
                    .jwtToken(jwt)
                    .build();

            AuthenticateOutput authenticateOutput = authenticationRestExport.authenticate(input);
            UserAuthority userAuthority = UserAuthority.builder()
                    .authority(authenticateOutput.getRole().toString().toUpperCase())
                    .build();

            LoggedUserDetails loggedUserDetails = LoggedUserDetails.builder()
                    .userAuthority(userAuthority)
                    .username(authenticateOutput.getUsername())
                    .password(authenticateOutput.getPassword())
                    .build();

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    loggedUserDetails,
                    null,
                    loggedUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (Exception ignored){}

        filterChain.doFilter(request, response);
    }
}
