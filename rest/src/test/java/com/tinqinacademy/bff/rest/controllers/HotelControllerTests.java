package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.model.enums.UserRole;
import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.bff.api.exception.exceptions.NotFoundException;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.FeignException;
import jakarta.validation.constraints.Max;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private CommentsRestExport commentsRestExport;
    @MockBean
    private AuthenticationRestExport authenticationRestExport;
    @MockBean
    private JwtUtil jwtUtil;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoomOk() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.getRoom(roomId))
                .thenReturn(new GetRoomOutput());

        mvc.perform(get(RestApiRoutes.HOTEL_GET_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRoomNotFound() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.getRoom(roomId))
                .thenThrow(new NotFoundException(String.format("Room with id:%s", roomId)));

        mvc.perform(get(RestApiRoutes.HOTEL_GET_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCheckAvailableRoomsOk() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        int bedCount = 1;
        BffBedSize bedSize = BffBedSize.KING_SIZE;
        BffBathroomType bathroomType = BffBathroomType.SHARED;
        when(hotelRestExport.checkAvailableRooms(
                startDate, endDate, bedCount, bedSize.toString(), bathroomType.toString()))
                .thenReturn(new CheckAvailableRoomsOutput());

        mvc.perform(get(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS)
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2025-01-01")
                        .param("bedCount", "1")
                        .param("bedSize", bedSize.toString())
                        .param("bathroomType", bathroomType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckAvailableRoomsBadRequest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        int bedCount = 1;
        BffBedSize bedSize = BffBedSize.KING_SIZE;
        BffBathroomType bathroomType = BffBathroomType.SHARED;
        when(hotelRestExport.checkAvailableRooms(
                startDate, endDate, bedCount, bedSize.toString(), bathroomType.toString()))
                .thenReturn(new CheckAvailableRoomsOutput());

        mvc.perform(get(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS)
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2025-01")
                        .param("bedCount", "1")
                        .param("bedSize", bedSize.toString())
                        .param("bathroomType", bathroomType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookRoomCreated() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .userId(userId)
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isCreated());
    }

    @Test
    public void testBookRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .userId(userId)
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testBookRoomForbidden() throws Exception {
        String jwtHeader = "Bearer 1234";
        String roomId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .userId(userId)
                .build();


        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        when(hotelRestExport.bookRoom(roomId, bookRoomInput))
                .thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_BOOK_ROOM, roomId)
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRoomInput))
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testUnbookRoomOk() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .bookingId(bookingId)
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.HOTEL_UNBOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isOk());
    }

    @Test
    public void testUnbookRoomBadRequest() throws Exception {
        String roomId = "1234";
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .bookingId(bookingId)
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.HOTEL_UNBOOK_ROOM, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testUnbookRoomForbiddenNoToken() throws Exception {
        String roomId = "1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .bookingId(bookingId)
                .build();

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.HOTEL_UNBOOK_ROOM, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testUnbookRoomForbiddenInvalidToken() throws Exception {
        String roomId = "1234";
        String jwtHeader = "Bearer 1234";

        String bookingId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput.builder()
                .userId(userId)
                .bookingId(bookingId)
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.unbookRoom(bookingId, input))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.HOTEL_UNBOOK_ROOM, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, "Bearer abc")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testAddCommentCreated() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentInput input = AddCommentInput.builder()
                .roomId(roomId)
                .authorId(userId)
                .content("example comment")
                .build();


        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(commentsRestExport.addComment(roomId, input))
                .thenReturn(AddCommentOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_ADD_COMMENT, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isCreated());
    }

    @Test
    public void testAddCommentBadRequest() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = "abc";
        String jwtHeader = "Bearer 1234";

        AddCommentInput input = AddCommentInput.builder()
                .roomId(roomId)
                .authorId(userId)
                .content("example comment")
                .build();


        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(commentsRestExport.addComment(roomId, input))
                .thenReturn(AddCommentOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_ADD_COMMENT, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCommentForbidden() throws Exception {
        String userId = UUID.randomUUID().toString();
        String roomId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        AddCommentInput input = AddCommentInput.builder()
                .roomId(roomId)
                .authorId(userId)
                .content("example comment")
                .build();


        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(commentsRestExport.addComment(roomId, input))
                .thenReturn(AddCommentOutput.builder().build());

        mvc.perform(post(RestApiRoutes.HOTEL_ADD_COMMENT, roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, "Bearer abc")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testEditCommentOk() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        EditCommentInput input = EditCommentInput.builder()
                .authorId(userId)
                .content("example comment")
                .build();


        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(commentsRestExport.editComment(commentId, input))
                .thenReturn(EditCommentOutput.builder().build());

        mvc.perform(patch(RestApiRoutes.HOTEL_EDIT_COMMENT, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isOk());
    }

    @Test
    public void testEditCommentBadRequest() throws Exception {
        String userId = "123";
        String commentId = UUID.randomUUID().toString();
        String jwtHeader = "Bearer 1234";

        EditCommentInput input = EditCommentInput.builder()
                .authorId(userId)
                .content("example comment")
                .build();


        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(userId)
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(commentsRestExport.editComment(commentId, input))
                .thenReturn(EditCommentOutput.builder().build());

        mvc.perform(patch(RestApiRoutes.HOTEL_EDIT_COMMENT, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
                .header(HttpHeaders.AUTHORIZATION, jwtHeader)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditCommentForbidden() throws Exception {
        String userId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();

        EditCommentInput input = EditCommentInput.builder()
                .authorId(userId)
                .content("example comment")
                .build();


        when(commentsRestExport.editComment(commentId, input))
                .thenReturn(EditCommentOutput.builder().build());


        mvc.perform(patch(RestApiRoutes.HOTEL_EDIT_COMMENT, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input))
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testGetCommentsOk() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(commentsRestExport.getComments(roomId))
                .thenReturn(GetCommentsOutput.builder()
                        .comments(List.of())
                        .build());

        mvc.perform(get(RestApiRoutes.HOTEL_GET_COMMENTS, roomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCommentsBadRequest() throws Exception {
        String roomId = "1234";

        when(commentsRestExport.getComments(roomId))
                .thenReturn(GetCommentsOutput.builder()
                        .comments(List.of())
                        .build());

        mvc.perform(get(RestApiRoutes.HOTEL_GET_COMMENTS, roomId))
                .andExpect(status().isBadRequest());
    }

}
