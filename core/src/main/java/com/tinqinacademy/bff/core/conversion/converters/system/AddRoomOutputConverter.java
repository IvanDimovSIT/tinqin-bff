package com.tinqinacademy.bff.core.conversion.converters.system;

import com.tinqinacademy.bff.api.operations.system.addroom.AddRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AddRoomOutputConverter extends BaseConverter<com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput,
        AddRoomOutput> {

    @Override
    protected AddRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput source) {
        return AddRoomOutput.builder()
                .build();
    }
}
