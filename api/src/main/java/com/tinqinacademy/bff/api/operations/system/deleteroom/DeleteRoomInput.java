package com.tinqinacademy.bff.api.operations.system.deleteroom;


import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomInput implements OperationInput {
    @NotEmpty
    private String id;
}
