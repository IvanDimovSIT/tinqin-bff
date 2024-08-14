package com.tinqinacademy.bff.api.operations.system.addroom;


import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
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
    private BffBedSize bedSize;
    @NotNull
    @ValidBathroomType
    private BffBathroomType bathroomType;
    @NotNull
    @Min(value = 1)
    @Max(value = 20)
    private Integer floor;
    @NotBlank
    @Pattern(regexp = "[0-9]{1,10}[A-Z]?", message = "Valid room numbers start with numbers and could end with a letter")
    private String roomNumber;
    @Positive
    @NotNull
    private BigDecimal price;
}
