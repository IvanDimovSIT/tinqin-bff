package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.BffGetRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetRoomOperationProcessor extends BaseOperationProcessor implements BffGetRoomOperation {
    private final HotelRestExport hotelRestExport;

    public GetRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                     Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffGetRoomOutput> process(BffGetRoomInput input) {
        return Try.of(() -> {
                    log.info("Start getRoom input:{}", input);
                    validate(input);

                    GetRoomOutput hotelOutput =
                            hotelRestExport.getRoom(input.getId());

                    BffGetRoomOutput output = conversionService.convert(hotelOutput, BffGetRoomOutput.class);

                    log.info("End getRoom result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
