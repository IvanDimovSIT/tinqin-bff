package com.tinqinacademy.bff.api.operations.hotel.getroom;

import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomInput implements OperationInput {
    @NotEmpty
    @UUID
    private String id;
}
