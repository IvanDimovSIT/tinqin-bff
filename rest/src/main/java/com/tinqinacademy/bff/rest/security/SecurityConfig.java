package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.bff.api.RestApiRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private void configureAdminAccess(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request
                .requestMatchers(HttpMethod.POST, RestApiRoutes.SYSTEM_REGISTER_VISITOR).hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, RestApiRoutes.SYSTEM_GET_VISITORS).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.SYSTEM_DELETE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, RestApiRoutes.SYSTEM_ADD_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, RestApiRoutes.SYSTEM_UPDATE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.SYSTEM_ADMIN_DELETE_COMMENT).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, RestApiRoutes.SYSTEM_ADMIN_EDIT_COMMENT).hasRole("ADMIN");

    }

    private void configureUserAccess(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request
                .requestMatchers(HttpMethod.POST, RestApiRoutes.HOTEL_BOOK_ROOM).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.HOTEL_UNBOOK_ROOM).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, RestApiRoutes.HOTEL_ADD_COMMENT).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, RestApiRoutes.HOTEL_EDIT_COMMENT).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS).hasAnyRole("USER", "ADMIN");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    configureAdminAccess(request);
                    configureUserAccess(request);
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
