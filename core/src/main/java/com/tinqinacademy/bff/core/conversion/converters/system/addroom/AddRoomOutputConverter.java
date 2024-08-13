package com.tinqinacademy.bff.core.conversion.converters.system.addroom;

import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class AddRoomOutputConverter extends BaseConverter<AddRoomOutput, BffAddRoomOutput> {

    @Override
    protected BffAddRoomOutput convertObject(AddRoomOutput source) {
        return BffAddRoomOutput.builder()
                .build();
    }
}
