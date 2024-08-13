package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOperation;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentOperation;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomInput;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomOutput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsOperation;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomOutput;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomInput;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOutput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorOperation;
import com.tinqinacademy.bff.api.operations.system.updateroom.BffUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.updateroom.BffUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.system.updateroom.BffUpdateRoomOutput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsInput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsOutput;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorInput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorOutput;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentInput;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOutput;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentInput;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class SystemController extends BaseController {
    private final BffDeleteRoomOperation deleteRoomOperation;
    private final BffPartialUpdateRoomOperation partialUpdateRoomOperation;
    private final BffAddRoomOperation addRoomOperation;
    private final BffUpdateRoomOperation updateRoomOperation;
    private final BffGetVisitorsOperation getVisitorsOperation;
    private final BffRegisterVisitorOperation registerVisitorOperation;
    private final BffAdminDeleteCommentOperation adminDeleteCommentOperation;
    private final BffAdminEditCommentOperation adminEditCommentOperation;

    public SystemController(JwtUtil jwtUtil, BffDeleteRoomOperation deleteRoomOperation,
                            BffPartialUpdateRoomOperation partialUpdateRoomOperation,
                            BffAddRoomOperation addRoomOperation, BffUpdateRoomOperation updateRoomOperation,
                            BffGetVisitorsOperation getVisitorsOperation,
                            BffRegisterVisitorOperation registerVisitorOperation,
                            BffAdminDeleteCommentOperation adminDeleteCommentOperation,
                            BffAdminEditCommentOperation adminEditCommentOperation) {
        super(jwtUtil);
        this.deleteRoomOperation = deleteRoomOperation;
        this.partialUpdateRoomOperation = partialUpdateRoomOperation;
        this.addRoomOperation = addRoomOperation;
        this.updateRoomOperation = updateRoomOperation;
        this.getVisitorsOperation = getVisitorsOperation;
        this.registerVisitorOperation = registerVisitorOperation;
        this.adminDeleteCommentOperation = adminDeleteCommentOperation;
        this.adminEditCommentOperation = adminEditCommentOperation;
    }

    @Operation(summary = "Registers a visitor as room renter", description = "Registers a visitor as room renter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found")
    })
    @PostMapping(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
    public ResponseEntity<?> registerVisitor(@RequestBody BffRegisterVisitorInput input) {
        Either<Errors, BffRegisterVisitorOutput> output = registerVisitorOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a room", description = "Deletes a room" +
            " is available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "room id not found"),
    })
    @DeleteMapping(RestApiRoutes.SYSTEM_DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        BffDeleteRoomInput input = BffDeleteRoomInput.builder()
                .id(roomId)
                .build();

        Either<Errors, BffDeleteRoomOutput> output = deleteRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Admin partial update of room data", description = "Admin partial update of room data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room data updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @PatchMapping(value = com.tinqinacademy.hotel.api.RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, consumes = {"application/json-patch+json", "application/json"})
    public ResponseEntity<?> partialUpdateRoom(@PathVariable String roomId, @RequestBody BffPartialUpdateRoomInput input) {
        BffPartialUpdateRoomInput partialUpdateRoomInput = input.toBuilder()
                .roomId(roomId)
                .build();

        Either<Errors, BffPartialUpdateRoomOutput> output = partialUpdateRoomOperation.process(partialUpdateRoomInput);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Creates a new room", description = "Admin creates a new room with specified " +
            "parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added room"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(RestApiRoutes.SYSTEM_ADD_ROOM)
    public ResponseEntity<?> addRoom(@RequestBody BffAddRoomInput input) {
        Either<Errors, BffAddRoomOutput> output = addRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Update room data", description = "Admin updates the info regarding a certain" +
            " room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updated room data"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @PutMapping(RestApiRoutes.SYSTEM_UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody BffUpdateRoomInput input) {
        BffUpdateRoomInput updateRoomInput = input.toBuilder()
                .roomId(roomId)
                .build();

        Either<Errors, BffUpdateRoomOutput> output = updateRoomOperation.process(updateRoomInput);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Finds info on visitors", description = "Admin only. Provides a report based on" +
            " various criteria. Provides info on when the room was booked and by whom. Can report when a user has" +
            " occupied rooms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(RestApiRoutes.SYSTEM_GET_VISITORS)
    public ResponseEntity<?> getVisitors(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String idCardNumber,
            @RequestParam(required = false) LocalDate idCardValidity,
            @RequestParam(required = false) String idCardIssueAuthority,
            @RequestParam(required = false) LocalDate idCardIssueDate,
            @RequestParam(required = false) String roomNumber
    ) {
        BffGetVisitorsInput input = BffGetVisitorsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueDate(idCardIssueDate)
                .roomNumber(roomNumber)
                .build();

        Either<Errors, BffGetVisitorsOutput> output = getVisitorsOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Admin deletes a comments", description = "Gets a list of comments left for a certain room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping(RestApiRoutes.SYSTEM_ADMIN_DELETE_COMMENT)
    public ResponseEntity<?> adminDeleteComment(@PathVariable String commentId) {
        BffAdminDeleteCommentInput input = BffAdminDeleteCommentInput.builder()
                .commentId(commentId)
                .build();

        Either<Errors, BffAdminDeleteCommentOutput> output = adminDeleteCommentOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Admin edits a comment", description = "Admin edits a certain comment left for a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping(RestApiRoutes.SYSTEM_ADMIN_EDIT_COMMENT)
    public ResponseEntity<?> adminEditComment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtHeader,
            @PathVariable String commentId,
            @RequestBody BffAdminEditCommentInput input) {
        BffAdminEditCommentInput adminEditCommentInput = input.toBuilder()
                .commentId(commentId)
                .adminId(extractUserIdFromToken(jwtHeader))
                .build();

        Either<Errors, BffAdminEditCommentOutput> output = adminEditCommentOperation.process(adminEditCommentInput);
        return mapToResponseEntity(output, HttpStatus.OK);
    }

}
