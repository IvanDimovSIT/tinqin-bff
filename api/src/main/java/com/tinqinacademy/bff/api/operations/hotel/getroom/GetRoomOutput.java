package com.tinqinacademy.bff.api.operations.hotel.getroom;



import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
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
public class GetRoomOutput implements OperationOutput {
    private String id;
    private String number;
    private Integer bedCount;
    private BedSize bedSize;
    private Integer floor;
    private BigDecimal price;
    private BathroomType bathroomType;
    private List<LocalDate> datesOccupied;
}
