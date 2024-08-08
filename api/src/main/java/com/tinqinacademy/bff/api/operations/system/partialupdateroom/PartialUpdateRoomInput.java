package com.tinqinacademy.bff.api.operations.system.partialupdateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
import com.tinqinacademy.bff.api.validation.bathroomtype.ValidBathroomType;
import com.tinqinacademy.bff.api.validation.bedsize.ValidBedSize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class PartialUpdateRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;
    @Min(value = 1)
    @Max(value = 10)
    private Integer bedCount;
    @ValidBedSize
    private BedSize bedSize;
    @ValidBathroomType
    private BathroomType bathroomType;
    @Min(value = 1)
    @Max(value = 20)
    private Integer floor;
    @Pattern(regexp = "[0-9]{1,10}[A-Z]?")
    private String roomNumber;
    @Positive
    private BigDecimal price;
}
