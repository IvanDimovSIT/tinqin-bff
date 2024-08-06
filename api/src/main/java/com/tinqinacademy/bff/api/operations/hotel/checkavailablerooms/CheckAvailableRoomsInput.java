package com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms;

import com.tinqinacademy.bff.api.validation.bedsize.*;
import com.tinqinacademy.bff.api.validation.bathroomtype.*;
import com.tinqinacademy.bff.api.model.enums.*;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckAvailableRoomsInput implements OperationInput {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(1)
    @Max(10)
    private Integer bedCount;
    @ValidBedSize
    private BedSize bedSize;
    @ValidBathroomType
    private BathroomType bathroomType;
}
