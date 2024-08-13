package com.tinqinacademy.bff.api.operations.system.deleteroom;


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
public class BffDeleteRoomInput implements OperationInput {
    @NotEmpty
    @UUID
    private String id;
}
