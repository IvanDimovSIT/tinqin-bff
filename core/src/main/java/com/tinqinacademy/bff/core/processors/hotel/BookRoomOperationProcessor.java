package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoomOperation {
    private final HotelRestExport hotelRestExport;

    public BookRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                      Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BookRoomOutput> process(BookRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput hotelInput =
                            conversionService.convert(input,
                                    com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput.class);

                    hotelInput.setUserId(input.getUserId());

                    com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput hotelOutput =
                            hotelRestExport.bookRoom(input.getRoomId(), hotelInput);

                    BookRoomOutput output = conversionService.convert(hotelOutput, BookRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
