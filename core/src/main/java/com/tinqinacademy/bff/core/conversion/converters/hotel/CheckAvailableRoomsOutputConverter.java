package com.tinqinacademy.bff.core.conversion.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class CheckAvailableRoomsOutputConverter extends BaseConverter<com.tinqinacademy.hotel.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput,
        CheckAvailableRoomsOutput> {

    @Override
    protected CheckAvailableRoomsOutput convertObject(
            com.tinqinacademy.hotel.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput source) {
        CheckAvailableRoomsOutput output = CheckAvailableRoomsOutput.builder()
                .ids(source.getIds())
                .build();

        return output;
    }
}
