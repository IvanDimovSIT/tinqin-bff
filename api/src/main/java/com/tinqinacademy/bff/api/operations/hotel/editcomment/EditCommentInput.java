package com.tinqinacademy.bff.api.operations.hotel.editcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class EditCommentInput implements OperationInput {
    @JsonIgnore
    @NotEmpty
    private String commentId;
    @JsonIgnore
    @NotEmpty
    private String authorId;
    @NotEmpty
    private String content;
}
