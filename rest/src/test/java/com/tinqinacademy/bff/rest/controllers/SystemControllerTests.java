package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.authenticate.AuthenticateOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.bff.api.RestApiRoutes;
import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.api.model.visitor.VisitorInput;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomInput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorInput;
import com.tinqinacademy.bff.api.operations.system.updateroom.BffUpdateRoomInput;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.FeignException;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SystemControllerTests {
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
    public void testRegisterVisitorOk() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterVisitorForbiddenInvalidRole() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorForbiddenInvalidToken() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorForbiddenNoToken() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of())
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegisterVisitorBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";
        BffRegisterVisitorInput bffRegisterVisitorInput = BffRegisterVisitorInput.builder()
                .visitorInputs(List.of(VisitorInput.builder().build()))
                .build();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        RegisterVisitorInput registerVisitorInput = RegisterVisitorInput.builder()
                .build();

        when(hotelRestExport.registerVisitor(registerVisitorInput))
                .thenReturn(RegisterVisitorOutput.builder().build());

        mvc.perform(post(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .content(objectMapper.writeValueAsString(bffRegisterVisitorInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteRoomOk() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());


        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRoomForbiddenInvalidToken() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());


        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteRoomForbiddenNoToken() throws Exception {
        String roomId = UUID.randomUUID().toString();

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_ROOM, roomId))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = "123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());


        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.deleteRoom(roomId))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPartialUpdateRoomOk() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        PartialUpdateRoomInput partialUpdateRoomInput = PartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.partialUpdateRoom(roomId, partialUpdateRoomInput))
                .thenReturn(PartialUpdateRoomOutput.builder().build());

        BffPartialUpdateRoomInput input = BffPartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        mvc.perform(patch(RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

    @Test
    void testPartialUpdateRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = "123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        PartialUpdateRoomInput partialUpdateRoomInput = PartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.partialUpdateRoom(roomId, partialUpdateRoomInput))
                .thenReturn(PartialUpdateRoomOutput.builder().build());

        BffPartialUpdateRoomInput input = BffPartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        mvc.perform(patch(RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPartialUpdateRoomForbiddenNotAdmin() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        PartialUpdateRoomInput partialUpdateRoomInput = PartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.partialUpdateRoom(roomId, partialUpdateRoomInput))
                .thenReturn(PartialUpdateRoomOutput.builder().build());

        BffPartialUpdateRoomInput input = BffPartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        mvc.perform(patch(RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    void testPartialUpdateRoomForbiddenInvalidToken() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        PartialUpdateRoomInput partialUpdateRoomInput = PartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenThrow(FeignException.class);

        when(hotelRestExport.partialUpdateRoom(roomId, partialUpdateRoomInput))
                .thenReturn(PartialUpdateRoomOutput.builder().build());

        BffPartialUpdateRoomInput input = BffPartialUpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .build();

        mvc.perform(patch(RestApiRoutes.SYSTEM_PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAddRoomCreated() throws Exception {
        String jwtHeader = "Bearer 123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        AddRoomInput addRoomInput = AddRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.addRoom(addRoomInput))
                .thenReturn(AddRoomOutput.builder().build());

        BffAddRoomInput input = BffAddRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(post(RestApiRoutes.SYSTEM_ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddRoomForbidden() throws Exception {
        String jwtHeader = "Bearer 123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        AddRoomInput addRoomInput = AddRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.addRoom(addRoomInput))
                .thenReturn(AddRoomOutput.builder().build());

        BffAddRoomInput input = BffAddRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(post(RestApiRoutes.SYSTEM_ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAddRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        AddRoomInput addRoomInput = AddRoomInput
                .builder()
                .roomNumber("   ")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.addRoom(addRoomInput))
                .thenReturn(AddRoomOutput.builder().build());

        BffAddRoomInput input = BffAddRoomInput
                .builder()
                .roomNumber("   ")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(post(RestApiRoutes.SYSTEM_ADD_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateRoomOk() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(UpdateRoomOutput.builder().build());

        BffUpdateRoomInput input = BffUpdateRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(put(RestApiRoutes.SYSTEM_UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRoomBadRequest() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = "123";

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(UpdateRoomOutput.builder().build());

        BffUpdateRoomInput input = BffUpdateRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(put(RestApiRoutes.SYSTEM_UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateRoomForbidden() throws Exception {
        String jwtHeader = "Bearer 123";
        String roomId = UUID.randomUUID().toString();

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("USER")
                        .build());

        UpdateRoomInput updateRoomInput = UpdateRoomInput
                .builder()
                .roomId(roomId)
                .roomNumber("543")
                .bathroomType(BathroomType.SHARED)
                .bedSize(BedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.updateRoom(roomId, updateRoomInput))
                .thenReturn(UpdateRoomOutput.builder().build());

        BffUpdateRoomInput input = BffUpdateRoomInput
                .builder()
                .roomNumber("543")
                .bathroomType(BffBathroomType.SHARED)
                .bedSize(BffBedSize.KING_SIZE)
                .floor(10)
                .price(BigDecimal.valueOf(1200))
                .bedCount(2)
                .build();

        mvc.perform(put(RestApiRoutes.SYSTEM_UPDATE_ROOM, roomId)
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetVisitorsOk() throws Exception {
        String jwtHeader = "Bearer 123";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(jwtUtil.decodeHeader(jwtHeader))
                .thenReturn(JwtToken.builder()
                        .id(UUID.randomUUID().toString())
                        .role("ADMIN")
                        .build());


        when(authenticationRestExport.authenticate(jwtHeader))
                .thenReturn(AuthenticateOutput.builder().build());

        when(hotelRestExport.getVisitors(startDate,endDate, null, null, null,
                null, null, null, null, null))
                .thenReturn(GetVisitorsOutput.builder().visitorOutputs(List.of()).build());



        mvc.perform(get(RestApiRoutes.SYSTEM_GET_VISITORS)
                        .param("startDate", String.valueOf(startDate))
                        .param("endDate", String.valueOf(endDate))
                        .header(HttpHeaders.AUTHORIZATION, jwtHeader))
                .andExpect(status().isOk());
    }

}
