package com.tinqinacademy.bff.core.conversion.converters.hotel.getroom;


import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.core.conversion.converters.enums.BathroomTypeToBffBathroomTypeConverter;
import com.tinqinacademy.bff.core.conversion.converters.enums.BedSizeToBffBedSizeConverter;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetRoomOutputConverter extends BaseConverter<GetRoomOutput, BffGetRoomOutput> {
    private final BathroomTypeToBffBathroomTypeConverter bffBathroomTypeConverter;
    private final BedSizeToBffBedSizeConverter bedSizeConverter;

    @Override
    protected BffGetRoomOutput convertObject(GetRoomOutput source) {
        BffGetRoomOutput output = BffGetRoomOutput.builder()
                .id(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .number(source.getNumber())
                .bathroomType(bffBathroomTypeConverter.convert(source.getBathroomType()))
                .bedCount(source.getBedCount())
                .bedSize(bedSizeConverter.convert(source.getBedSize()))
                .datesOccupied(source.getDatesOccupied())
                .build();

        return output;
    }
}
