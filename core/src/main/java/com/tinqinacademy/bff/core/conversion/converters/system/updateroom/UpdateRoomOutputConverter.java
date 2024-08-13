package com.tinqinacademy.bff.core.conversion.converters.system.updateroom;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.api.operations.system.updateroom.BffUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomOutputConverter extends BaseConverter<UpdateRoomOutput, BffUpdateRoomOutput> {
    @Override
    protected BffUpdateRoomOutput convertObject(UpdateRoomOutput source) {
        return BffUpdateRoomOutput.builder()
                .id(source.getId())
                .build();
    }
}
