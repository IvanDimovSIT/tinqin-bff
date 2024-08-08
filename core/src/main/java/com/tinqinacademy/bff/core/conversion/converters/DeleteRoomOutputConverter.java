package com.tinqinacademy.bff.core.conversion.converters;

import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomOutputConverter extends BaseConverter<
        com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput,
        DeleteRoomOutput> {

    @Override
    protected DeleteRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput source) {
        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        return output;
    }
}
