package com.tinqinacademy.bff.api.operations.hotel.unbookroom;


import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomInput implements OperationInput {
    @NotEmpty
    private String bookingId;
}
