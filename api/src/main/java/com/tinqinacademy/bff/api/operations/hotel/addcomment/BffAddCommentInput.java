package com.tinqinacademy.bff.api.operations.hotel.addcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BffAddCommentInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String roomId;
    @JsonIgnore
    @NotBlank
    private String authorId;
    @NotBlank
    private String content;
}
