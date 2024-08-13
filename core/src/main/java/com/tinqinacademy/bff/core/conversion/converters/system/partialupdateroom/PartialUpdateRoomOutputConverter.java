package com.tinqinacademy.bff.core.conversion.converters.system.partialupdateroom;

import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class PartialUpdateRoomOutputConverter extends BaseConverter<PartialUpdateRoomOutput, BffPartialUpdateRoomOutput> {

    @Override
    protected BffPartialUpdateRoomOutput convertObject(PartialUpdateRoomOutput source) {
        BffPartialUpdateRoomOutput output = BffPartialUpdateRoomOutput.builder()
                .id(source.getId())
                .build();

        return output;
    }
}
