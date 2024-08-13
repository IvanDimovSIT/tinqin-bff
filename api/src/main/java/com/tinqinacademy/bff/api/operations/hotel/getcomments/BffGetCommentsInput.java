package com.tinqinacademy.bff.api.operations.hotel.getcomments;

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
public class BffGetCommentsInput implements OperationInput {
    @NotEmpty
    @UUID
    private String roomId;
}
