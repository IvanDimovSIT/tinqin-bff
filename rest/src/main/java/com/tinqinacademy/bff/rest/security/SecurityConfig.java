package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.bff.api.RestApiRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] ADMIN_URLS = {
            RestApiRoutes.SYSTEM_DELETE_ROOM,
            RestApiRoutes.SYSTEM_ADD_ROOM,
            RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM,
            RestApiRoutes.SYSTEM_UPDATE_ROOM,
            RestApiRoutes.SYSTEM_REGISTER_VISITOR,
            RestApiRoutes.HOTEL_GET_ROOM,
            RestApiRoutes.SYSTEM_ADMIN_DELETE_COMMENT,
            RestApiRoutes.SYSTEM_ADMIN_EDIT_COMMENT,
            RestApiRoutes.SYSTEM_GET_VISITORS,
    };
    private final String[] USER_URLS = {
            RestApiRoutes.HOTEL_ADD_COMMENT,
            RestApiRoutes.HOTEL_BOOK_ROOM,
            RestApiRoutes.HOTEL_UNBOOK_ROOM,
            RestApiRoutes.HOTEL_EDIT_COMMENT,


    };
    //private static final String[] PUBLIC_URLS = {
    //        "/swagger-ui.html",
    //        "/swagger-ui/**",
    //        "/v3/api-docs/**",
    //        "/webjars/**"
    //};

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(ADMIN_URLS).hasAuthority("ADMIN");
                    request.requestMatchers(USER_URLS).hasAnyAuthority("USER", "ADMIN");
                    //request.requestMatchers(PUBLIC_URLS).permitAll();
                    request.anyRequest().permitAll();
                })
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
