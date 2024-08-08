package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SystemController extends BaseController {
    private final DeleteRoomOperation deleteRoomOperation;
    private final PartialUpdateRoomOperation partialUpdateRoomOperation;

    @Operation(summary = "Deletes a room", description = "Deletes a room" +
            " is available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "room id not found"),
    })
    @DeleteMapping(RestApiRoutes.SYSTEM_DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .id(roomId)
                .build();

        Either<Errors, DeleteRoomOutput> output = deleteRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Admin partial update of room data", description = "Admin partial update of room data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room data updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @PatchMapping(value = com.tinqinacademy.hotel.api.RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, consumes = {"application/json-patch+json", "application/json"})
    public ResponseEntity<?> partialUpdateRoom(@PathVariable String roomId, @RequestBody PartialUpdateRoomInput input) {
        PartialUpdateRoomInput partialUpdateRoomInput = input.toBuilder()
                .roomId(roomId)
                .build();

        Either<Errors, PartialUpdateRoomOutput> output = partialUpdateRoomOperation.process(partialUpdateRoomInput);

        return mapToResponseEntity(output, HttpStatus.OK);
    }
}
