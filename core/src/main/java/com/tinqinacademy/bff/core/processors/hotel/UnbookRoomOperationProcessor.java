package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomOutput;
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
public class UnbookRoomOperationProcessor extends BaseOperationProcessor implements UnbookRoomOperation {
    private final HotelRestExport hotelRestExport;

    public UnbookRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, UnbookRoomOutput> process(UnbookRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput hotelOutput =
                            hotelRestExport.unbookRoom(input.getBookingId());

                    UnbookRoomOutput output = conversionService.convert(hotelOutput, UnbookRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
