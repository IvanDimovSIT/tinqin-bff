package com.tinqinacademy.bff.api.operations.system.partialupdateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.api.validation.bathroomtype.ValidBathroomType;
import com.tinqinacademy.bff.api.validation.bedsize.ValidBedSize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BffPartialUpdateRoomInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String roomId;
    @Min(value = 1)
    @Max(value = 10)
    private Integer bedCount;
    @ValidBedSize
    private BffBedSize bedSize;
    @ValidBathroomType
    private BffBathroomType bathroomType;
    @Min(value = 1)
    @Max(value = 20)
    private Integer floor;
    @Pattern(regexp = "[0-9]{1,10}[A-Z]?")
    private String roomNumber;
    @Positive
    private BigDecimal price;
}
