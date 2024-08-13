package com.tinqinacademy.bff.api.operations.system.admineditcomment;

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
public class BffAdminEditCommentInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String commentId;
    @JsonIgnore
    @NotEmpty
    private String adminId;
    @NotEmpty
    private String roomId;
    @NotEmpty
    private String content;
}
