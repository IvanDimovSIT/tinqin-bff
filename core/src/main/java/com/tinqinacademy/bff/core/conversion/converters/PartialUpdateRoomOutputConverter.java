package com.tinqinacademy.bff.core.conversion.converters;

import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class PartialUpdateRoomOutputConverter extends BaseConverter<
        com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput,
        PartialUpdateRoomOutput> {

    @Override
    protected PartialUpdateRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput source) {
        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .id(source.getId())
                .build();

        return output;
    }
}
