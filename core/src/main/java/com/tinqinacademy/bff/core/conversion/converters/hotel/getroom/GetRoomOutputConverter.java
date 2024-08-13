package com.tinqinacademy.bff.core.conversion.converters.hotel.getroom;


import com.tinqinacademy.bff.api.model.enums.BathroomType;
import com.tinqinacademy.bff.api.model.enums.BedSize;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomOutputConverter extends BaseConverter<GetRoomOutput, BffGetRoomOutput> {


    @Override
    protected BffGetRoomOutput convertObject(GetRoomOutput source) {
        BffGetRoomOutput output = BffGetRoomOutput.builder()
                .id(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .number(source.getNumber())
                .bathroomType(source.getBathroomType() == null? null:
                        BathroomType.getCode(source.getBathroomType().toString()))
                .bedCount(source.getBedCount())
                .bedSize(source.getBedSize() == null? null:
                        BedSize.getCode(source.getBedSize().toString()))
                .datesOccupied(source.getDatesOccupied())
                .build();

        return output;
    }
}
