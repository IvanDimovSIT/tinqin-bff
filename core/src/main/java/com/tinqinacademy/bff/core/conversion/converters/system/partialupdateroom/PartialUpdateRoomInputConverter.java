package com.tinqinacademy.bff.core.conversion.converters.system.partialupdateroom;

import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class PartialUpdateRoomInputConverter extends BaseConverter<BffPartialUpdateRoomInput, PartialUpdateRoomInput> {

    @Override
    protected PartialUpdateRoomInput convertObject(BffPartialUpdateRoomInput source) {
        return PartialUpdateRoomInput.builder()
                .roomId(source.getRoomId())
                .bathroomType(source.getBathroomType() == null? null: BathroomType.getCode(source.getBathroomType().toString()))
                .bedCount(source.getBedCount())
                .bedSize(source.getBedSize() == null? null: BedSize.getCode(source.getBedSize().toString()))
                .roomNumber(source.getRoomNumber())
                .price(source.getPrice())
                .floor(source.getFloor())
                .build();
    }
}
