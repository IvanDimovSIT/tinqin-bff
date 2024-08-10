package com.tinqinacademy.bff.core.conversion.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomOutputConverter extends
        BaseConverter<com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput, UnbookRoomOutput> {

    @Override
    protected UnbookRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput source) {
        UnbookRoomOutput output = UnbookRoomOutput.builder()
                .build();

        return output;
    }
}
