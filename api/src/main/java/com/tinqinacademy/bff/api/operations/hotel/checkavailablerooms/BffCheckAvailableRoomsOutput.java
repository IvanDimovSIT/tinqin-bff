package com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffCheckAvailableRoomsOutput implements OperationOutput {
    private List<String> ids;
}
