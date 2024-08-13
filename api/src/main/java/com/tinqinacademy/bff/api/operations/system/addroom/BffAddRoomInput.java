package com.tinqinacademy.bff.api.operations.system.addroom;


import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
import com.tinqinacademy.bff.api.validation.bathroomtype.ValidBathroomType;
import com.tinqinacademy.bff.api.validation.bedsize.ValidBedSize;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffAddRoomInput implements OperationInput {
    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer bedCount;
    @NotNull
    @ValidBedSize
    private BedSize bedSize;
    @NotNull
    @ValidBathroomType
    private BathroomType bathroomType;
    @NotNull
    @Min(value = 1)
    @Max(value = 20)
    private Integer floor;
    @NotEmpty
    @Pattern(regexp = "[0-9]{1,10}[A-Z]?", message = "Valid room numbers start with numbers and could end with a letter")
    private String roomNumber;
    @Positive
    @NotNull
    private BigDecimal price;
}
