package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.model.visitor.VisitorInput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorInput;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SystemControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private CommentsRestExport commentsRestExport;
    @MockBean
    private AuthenticationRestExport authenticationRestExport;
    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterVisitorOk() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterVisitorForbiddenInvalidRole() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorForbiddenInvalidToken() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorForbiddenNoToken() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of(VisitorInput.builder().build()))
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
