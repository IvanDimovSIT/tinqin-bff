package com.tinqinacademy.bff.core.conversion.converters.hotel.unbookroom;

import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomInputConverter extends BaseConverter<BffUnbookRoomInput, UnbookRoomInput> {
    @Override
    protected UnbookRoomInput convertObject(BffUnbookRoomInput source) {
        return UnbookRoomInput.builder()
                .userId(source.getUserId())
                .build();
    }
}
