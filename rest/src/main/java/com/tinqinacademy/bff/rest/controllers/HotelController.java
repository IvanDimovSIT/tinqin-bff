package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOperation;

import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.*;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final GetRoomOperation getRoomOperation;
    private final CheckAvailableRoomsOperation checkAvailableRoomsOperation;
    private final UnbookRoomOperation unbookRoomOperation;

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

    @Operation(summary = "Checks whether a room is available for a certain period", description = "Checks whether a" +
            " room is available for a certain period. Room requirements should come as query parameters in URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS)
    public ResponseEntity<?> checkAvailableRooms(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Integer bedCount,
            @RequestParam(required = false) String bedSize,
            @RequestParam(required = false) String bathroomType) {
        CheckAvailableRoomsInput input = CheckAvailableRoomsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(bedSize ==null? null: BedSize.getCode(bedSize))
                .bathroomType(bathroomType == null? null: BathroomType.getCode(bathroomType))
                .build();


        Either<Errors, CheckAvailableRoomsOutput> output =
                checkAvailableRoomsOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Unbooks a booked room", description = "Unbooks a room that the user has already" +
            " booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Booking id not found"),
    })
    @DeleteMapping(RestApiRoutes.HOTEL_UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable String bookingId) {
        UnbookRoomInput input = UnbookRoomInput.builder()
                .bookingId(bookingId)
                .build();

        Either<Errors, UnbookRoomOutput> output = unbookRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }
}