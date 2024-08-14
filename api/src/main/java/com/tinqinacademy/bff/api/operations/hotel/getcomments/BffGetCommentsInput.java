package com.tinqinacademy.bff.api.operations.hotel.getcomments;

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
public class BffGetCommentsInput implements OperationInput {
    @NotBlank
    @UUID
    private String roomId;
}
