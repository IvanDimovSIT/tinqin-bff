package com.tinqinacademy.bff.api.operations.hotel.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BookRoomInput implements OperationInput {
    @JsonIgnore
    @NotEmpty
    private String roomId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @JsonIgnore
    @NotEmpty
    private String userId;
}
