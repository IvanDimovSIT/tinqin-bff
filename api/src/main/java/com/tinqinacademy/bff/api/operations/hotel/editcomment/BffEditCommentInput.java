package com.tinqinacademy.bff.api.operations.hotel.editcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BffEditCommentInput implements OperationInput {
    @JsonIgnore
    @NotBlank
    @UUID
    private String commentId;
    @JsonIgnore
    @NotBlank
    @UUID
    private String authorId;
    @NotBlank
    private String content;
}
