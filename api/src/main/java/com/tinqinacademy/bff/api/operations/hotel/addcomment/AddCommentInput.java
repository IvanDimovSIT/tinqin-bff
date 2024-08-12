package com.tinqinacademy.bff.api.operations.hotel.addcomment;

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
public class AddCommentInput implements OperationInput {
    @JsonIgnore
    private String roomId;
    @JsonIgnore
    @NotEmpty
    private String authorId;
    @NotEmpty
    private String content;
}
