package com.tinqinacademy.bff.api.operations.system.admindeletecomment;

import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class AdminDeleteCommentInput implements OperationInput {
    @NotEmpty
    private String commentId;
}
