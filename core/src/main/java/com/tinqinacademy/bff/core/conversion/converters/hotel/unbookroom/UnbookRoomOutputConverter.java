package com.tinqinacademy.bff.core.conversion.converters.hotel.unbookroom;

import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomOutputConverter extends BaseConverter<UnbookRoomOutput, BffUnbookRoomOutput> {

    @Override
    protected BffUnbookRoomOutput convertObject(UnbookRoomOutput source) {
        BffUnbookRoomOutput output = BffUnbookRoomOutput.builder()
                .build();

        return output;
    }
}
