package com.tinqinacademy.bff.api.operations.hotel.editcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class EditCommentInput implements OperationInput {
    @JsonIgnore
    @NotEmpty
    @UUID
    private String commentId;
    @JsonIgnore
    @NotEmpty
    @UUID
    private String authorId;
    @NotEmpty
    private String content;
}
