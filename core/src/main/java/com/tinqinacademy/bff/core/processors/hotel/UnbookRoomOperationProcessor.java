package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.BffUnbookRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnbookRoomOperationProcessor extends BaseOperationProcessor implements BffUnbookRoomOperation {
    private final HotelRestExport hotelRestExport;

    public UnbookRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffUnbookRoomOutput> process(BffUnbookRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    UnbookRoomInput hotelInput = conversionService.convert(input, UnbookRoomInput.class);
                    UnbookRoomOutput hotelOutput = hotelRestExport.unbookRoom(input.getBookingId(), hotelInput);

                    BffUnbookRoomOutput output = conversionService.convert(hotelOutput, BffUnbookRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
