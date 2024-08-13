package com.tinqinacademy.bff.core.conversion.converters.hotel.bookroom;

import com.tinqinacademy.bff.api.operations.hotel.bookroom.BffBookRoomInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomInputConverter extends BaseConverter<BffBookRoomInput, BookRoomInput> {
    @Override
    protected BookRoomInput convertObject(BffBookRoomInput source) {
        return BookRoomInput.builder()
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .build();
    }
}
