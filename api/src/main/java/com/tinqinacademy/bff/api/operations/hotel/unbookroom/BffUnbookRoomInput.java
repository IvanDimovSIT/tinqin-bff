package com.tinqinacademy.bff.api.operations.hotel.unbookroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffUnbookRoomInput implements OperationInput {
    @JsonIgnore
    @UUID
    @NotBlank
    private String userId;
    @NotBlank
    @UUID
    private String bookingId;
}
