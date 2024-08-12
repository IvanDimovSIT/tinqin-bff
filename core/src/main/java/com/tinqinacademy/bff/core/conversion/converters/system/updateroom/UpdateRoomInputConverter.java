package com.tinqinacademy.bff.core.conversion.converters.system.updateroom;

import com.tinqinacademy.bff.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomInputConverter extends BaseConverter<UpdateRoomInput,
        com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput> {
    @Override
    protected com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput convertObject(UpdateRoomInput source) {
        return com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput.builder()
                .roomId(source.getRoomId())
                .roomNumber(source.getRoomNumber())
                .bathroomType(source.getBathroomType() == null ? null : BathroomType.getCode(source.getBathroomType().toString()))
                .bedSize(source.getBedSize() == null ? null : BedSize.getCode(source.getBedSize().toString()))
                .bedCount(source.getBedCount())
                .floor(source.getFloor())
                .price(source.getPrice())
                .build();
    }
}
