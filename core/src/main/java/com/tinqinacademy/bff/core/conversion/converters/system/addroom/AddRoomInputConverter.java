package com.tinqinacademy.bff.core.conversion.converters.system.addroom;


import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import org.springframework.stereotype.Component;

@Component
public class AddRoomInputConverter extends BaseConverter<BffAddRoomInput, AddRoomInput> {

    @Override
    protected AddRoomInput convertObject(BffAddRoomInput source) {
        return AddRoomInput.builder()
                .roomNumber(source.getRoomNumber())
                .bathroomType(source.getBathroomType() == null? null: BathroomType.getCode(source.getBathroomType().toString()))
                .floor(source.getFloor())
                .bedSize(source.getBedSize() == null? null: BedSize.getCode(source.getBedSize().toString()))
                .price(source.getPrice())
                .bedCount(source.getBedCount())
                .build();
    }
}
