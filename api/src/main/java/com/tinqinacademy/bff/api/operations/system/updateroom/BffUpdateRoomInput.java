package com.tinqinacademy.bff.api.operations.system.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.api.validation.bathroomtype.ValidBathroomType;
import com.tinqinacademy.bff.api.validation.bedsize.ValidBedSize;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BffUpdateRoomInput implements OperationInput {
    @JsonIgnore
    @UUID
    private String roomId;
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
    @Pattern(regexp = "[0-9]{1,10}[A-Z]?")
    private String roomNumber;
    @Positive
    @NotNull
    private BigDecimal price;
}
