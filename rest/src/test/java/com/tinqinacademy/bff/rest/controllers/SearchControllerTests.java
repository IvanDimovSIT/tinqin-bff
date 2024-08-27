package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsInput;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOutput;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.search.restexport.SearchRestExport;
import feign.FeignException;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private SearchRestExport searchRestExport;
    @MockBean
    private AuthenticationRestExport authenticationRestExport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCommentsOk() throws Exception {
        String word = "word";
        String jwtHeader = "Bearer 1234";
        UUID userId = UUID.randomUUID();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId.toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindCommentsNotFound() throws Exception {
        String word = "";
        String jwtHeader = "Bearer 1234";
        UUID userId = UUID.randomUUID();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId.toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCommentsBadRequest() throws Exception {
        String word = "   ";
        String jwtHeader = "Bearer 1234";
        UUID userId = UUID.randomUUID();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId.toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindCommentsForbidden() throws Exception {
        String word = "   ";
        String jwtHeader = "Bearer 1234";
        UUID userId = UUID.randomUUID();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId.toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isForbidden());
    }
}
