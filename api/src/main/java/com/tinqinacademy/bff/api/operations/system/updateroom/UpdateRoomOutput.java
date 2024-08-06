package com.tinqinacademy.bff.api.operations.system.updateroom;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomOutput implements OperationOutput {
    private String id;
}
