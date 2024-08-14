package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BffBookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.BffCheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.*;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOutput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomOutput;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BffBookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BffBookRoomOutput;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsInput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsOutput;
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
public class HotelController extends BaseController {
    private final BffGetRoomOperation getRoomOperation;
    private final BffCheckAvailableRoomsOperation checkAvailableRoomsOperation;
    private final BffUnbookRoomOperation unbookRoomOperation;
    private final BffBookRoomOperation bookRoomOperation;
    private final BffAddCommentOperation addCommentOperation;
    private final BffEditCommentOperation editCommentOperation;
    private final BffGetCommentsOperation getCommentsOperation;

    public HotelController(JwtUtil jwtUtil, BffGetRoomOperation getRoomOperation,
                           BffCheckAvailableRoomsOperation checkAvailableRoomsOperation,
                           BffUnbookRoomOperation unbookRoomOperation, BffBookRoomOperation bookRoomOperation,
                           BffAddCommentOperation addCommentOperation, BffEditCommentOperation editCommentOperation,
                           BffGetCommentsOperation getCommentsOperation) {
        super(jwtUtil);
        this.getRoomOperation = getRoomOperation;
        this.checkAvailableRoomsOperation = checkAvailableRoomsOperation;
        this.unbookRoomOperation = unbookRoomOperation;
        this.bookRoomOperation = bookRoomOperation;
        this.addCommentOperation = addCommentOperation;
        this.editCommentOperation = editCommentOperation;
        this.getCommentsOperation = getCommentsOperation;
    }

    @Operation(summary = "Returns basic info for a room", description = "Returns basic info for a room with " +
            "specified id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found with booking information"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @GetMapping(RestApiRoutes.HOTEL_GET_ROOM)
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        BffGetRoomInput input = BffGetRoomInput.builder()
                .id(roomId)
                .build();

        Either<Errors, BffGetRoomOutput> output = getRoomOperation.process(input);
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
        BffCheckAvailableRoomsInput input = BffCheckAvailableRoomsInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(bedSize ==null? null: BedSize.getCode(bedSize))
                .bathroomType(bathroomType == null? null: BathroomType.getCode(bathroomType))
                .build();


        Either<Errors, BffCheckAvailableRoomsOutput> output =
                checkAvailableRoomsOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Books the room", description = "Books the room specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room booked"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Room id not found"),
    })
    @PostMapping(RestApiRoutes.HOTEL_BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtHeader,
                                      @PathVariable String roomId, @RequestBody BffBookRoomInput bookRoomInput) {
        BffBookRoomInput input = bookRoomInput.toBuilder()
                .roomId(roomId)
                .userId(extractUserIdFromToken(jwtHeader))
                .build();

        Either<Errors, BffBookRoomOutput> output = bookRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Unbooks a booked room", description = "Unbooks a room that the user has already" +
            " booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Booking id not found"),
    })
    @DeleteMapping(RestApiRoutes.HOTEL_UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtHeader,
                                        @PathVariable String bookingId) {
        BffUnbookRoomInput input = BffUnbookRoomInput.builder()
                .bookingId(bookingId)
                .userId(extractUserIdFromToken(jwtHeader))
                .build();

        Either<Errors, BffUnbookRoomOutput> output = unbookRoomOperation.process(input);

        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Leaves a comment", description = "Leaves a comment regarding a certain room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(RestApiRoutes.HOTEL_ADD_COMMENT)
    public ResponseEntity<?> addComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtHeader,
                                        @PathVariable String roomId, @RequestBody BffAddCommentInput input) {
        BffAddCommentInput addCommentInput = input.toBuilder()
                .roomId(roomId)
                .authorId(extractUserIdFromToken(jwtHeader))
                .build();

        Either<Errors, BffAddCommentOutput> output = addCommentOperation.process(addCommentInput);
        return mapToResponseEntity(output, HttpStatus.CREATED);
    }

    @Operation(summary = "User edits a comment", description = "User can edit own comment left for certain room. " +
            "Last edited date is updated. Info regarding user edited is updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping(RestApiRoutes.HOTEL_EDIT_COMMENT)
    public ResponseEntity<?> editComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtHeader,
                                         @PathVariable String commentId, @RequestBody BffEditCommentInput input) {
        BffEditCommentInput editCommentInput = input.toBuilder()
                .commentId(commentId)
                .authorId(extractUserIdFromToken(jwtHeader))
                .build();

        Either<Errors, BffEditCommentOutput> output = editCommentOperation.process(editCommentInput);
        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Retrieves comments", description = "Gets a list of comments left for a certain room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(RestApiRoutes.HOTEL_GET_COMMENTS)
    public ResponseEntity<?> getComments(@PathVariable String roomId) {
        BffGetCommentsInput input = BffGetCommentsInput.builder()
                .roomId(roomId)
                .build();

        Either<Errors, BffGetCommentsOutput> output = getCommentsOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }
}
