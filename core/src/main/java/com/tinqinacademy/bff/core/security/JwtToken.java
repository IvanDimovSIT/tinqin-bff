package com.tinqinacademy.bff.core.security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class JwtToken {
    private String id;
    private String role;
}
