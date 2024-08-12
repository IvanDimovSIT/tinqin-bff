package com.tinqinacademy.bff.core.conversion.converters.system.addroom;


import com.tinqinacademy.bff.api.operations.system.addroom.AddRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import org.springframework.stereotype.Component;

@Component
public class AddRoomInputConverter extends BaseConverter<AddRoomInput,
        com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput> {

    @Override
    protected com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput convertObject(AddRoomInput source) {
        return com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput.builder()
                .roomNumber(source.getRoomNumber())
                .bathroomType(source.getBathroomType() == null? null: BathroomType.getCode(source.getBathroomType().toString()))
                .floor(source.getFloor())
                .bedSize(source.getBedSize() == null? null: BedSize.getCode(source.getBedSize().toString()))
                .price(source.getPrice())
                .bedCount(source.getBedCount())
                .build();
    }
}
