package com.tinqinacademy.bff.api.operations.hotel.getroom;



import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffGetRoomOutput implements OperationOutput {
    private String id;
    private String number;
    private Integer bedCount;
    private BffBedSize bedSize;
    private Integer floor;
    private BigDecimal price;
    private BffBathroomType bathroomType;
    private List<LocalDate> datesOccupied;
}
