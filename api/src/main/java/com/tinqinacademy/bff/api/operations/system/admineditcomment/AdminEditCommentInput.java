package com.tinqinacademy.bff.api.operations.system.admineditcomment;

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
public class AdminEditCommentInput implements OperationInput {
    @JsonIgnore
    private String commentId;
    @JsonIgnore
    @NotEmpty
    private String adminId;
    @NotEmpty
    private String roomId;
    @NotEmpty
    private String content;
}
