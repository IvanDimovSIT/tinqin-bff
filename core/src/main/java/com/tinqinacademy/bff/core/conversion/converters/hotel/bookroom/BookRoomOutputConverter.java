package com.tinqinacademy.bff.core.conversion.converters.hotel.bookroom;

import com.tinqinacademy.bff.api.operations.hotel.bookroom.BffBookRoomOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomOutputConverter extends BaseConverter<BookRoomOutput, BffBookRoomOutput> {

    @Override
    protected BffBookRoomOutput convertObject(BookRoomOutput source) {
        return BffBookRoomOutput.builder()
                .build();
    }
}
