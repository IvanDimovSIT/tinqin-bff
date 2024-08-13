package com.tinqinacademy.bff.core.conversion.converters.hotel.checkavailablerooms;

import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.BffCheckAvailableRoomsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import org.springframework.stereotype.Component;

@Component
public class CheckAvailableRoomsOutputConverter extends BaseConverter<CheckAvailableRoomsOutput, BffCheckAvailableRoomsOutput> {

    @Override
    protected BffCheckAvailableRoomsOutput convertObject(
            CheckAvailableRoomsOutput source) {
        BffCheckAvailableRoomsOutput output = BffCheckAvailableRoomsOutput.builder()
                .ids(source.getIds())
                .build();

        return output;
    }
}
