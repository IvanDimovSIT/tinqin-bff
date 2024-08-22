package com.tinqinacademy.bff.api.operations.search.findcomments;


import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffFindCommentsInput implements OperationInput {
    @NotBlank
    private String word;
}
