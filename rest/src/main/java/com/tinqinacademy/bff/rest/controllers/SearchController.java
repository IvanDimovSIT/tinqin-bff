package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsInput;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOperation;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOutput;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController extends BaseController {
    private final BffFindCommentsOperation findCommentsOperation;

    public SearchController(JwtUtil jwtUtil, BffFindCommentsOperation findCommentsOperation) {
        super(jwtUtil);
        this.findCommentsOperation = findCommentsOperation;
    }

    @Operation(summary = "Find comments for word", description = "Finds comments for search word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS)
    public ResponseEntity<?> findComments(@PathVariable String word) {
        BffFindCommentsInput input = BffFindCommentsInput.builder()
                .word(word)
                .build();

        Either<Errors, BffFindCommentsOutput> output = findCommentsOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }
}
