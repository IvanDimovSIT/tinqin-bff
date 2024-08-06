package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOperation;

import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final GetRoomOperation getRoomOperation;

    @Operation(summary = "Returns basic info for a room", description = "Returns basic info for a room with " +
            "specified id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found with booking information"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @GetMapping(RestApiRoutes.HOTEL_GET_ROOM)
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        GetRoomInput input = GetRoomInput.builder()
                .id(roomId)
                .build();

        Either<Errors, GetRoomOutput> output = getRoomOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }
}
