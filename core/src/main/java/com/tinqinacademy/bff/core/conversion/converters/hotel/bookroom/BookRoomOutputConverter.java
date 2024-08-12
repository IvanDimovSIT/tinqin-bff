package com.tinqinacademy.bff.core.conversion.converters.hotel.bookroom;

import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class BookRoomOutputConverter extends BaseConverter<com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput,
        BookRoomOutput> {

    @Override
    protected BookRoomOutput convertObject(com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput source) {
        return BookRoomOutput.builder()
                .build();
    }
}
