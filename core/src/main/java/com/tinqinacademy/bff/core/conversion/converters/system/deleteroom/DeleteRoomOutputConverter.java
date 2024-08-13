package com.tinqinacademy.bff.core.conversion.converters.system.deleteroom;

import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomOutputConverter extends BaseConverter<DeleteRoomOutput, BffDeleteRoomOutput> {

    @Override
    protected BffDeleteRoomOutput convertObject(DeleteRoomOutput source) {
        BffDeleteRoomOutput output = BffDeleteRoomOutput.builder()
                .build();

        return output;
    }
}
