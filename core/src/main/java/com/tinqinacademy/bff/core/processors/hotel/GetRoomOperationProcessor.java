package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class GetRoomOperationProcessor extends BaseOperationProcessor implements GetRoomOperation {
    private final HotelRestExport hotelRestExport;

    public GetRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                     Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, GetRoomOutput> process(GetRoomInput input) {
        return Try.of(() -> {
                    log.info("Start getRoom input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput hotelOutput =
                            hotelRestExport.getRoom(input.getId());

                    GetRoomOutput output = conversionService.convert(hotelOutput, GetRoomOutput.class);

                    log.info("End getRoom result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
