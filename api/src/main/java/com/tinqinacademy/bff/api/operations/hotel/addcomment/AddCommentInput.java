package com.tinqinacademy.bff.api.operations.hotel.addcomment;

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
public class AddCommentInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String roomId;
    @JsonIgnore
    @NotEmpty
    private String authorId;
    @NotEmpty
    private String content;
}
