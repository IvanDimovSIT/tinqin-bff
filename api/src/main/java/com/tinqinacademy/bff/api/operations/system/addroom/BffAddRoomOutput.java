package com.tinqinacademy.bff.api.operations.system.addroom;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffAddRoomOutput implements OperationOutput {
    private String id;
}
