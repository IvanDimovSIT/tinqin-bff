package com.tinqinacademy.bff.core.conversion.converters.system;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.api.operations.system.updateroom.UpdateRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomOutputConverter extends BaseConverter<com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput,
        UpdateRoomOutput> {
    @Override
    protected UpdateRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput source) {
        return UpdateRoomOutput.builder()
                .id(source.getId())
                .build();
    }
}
