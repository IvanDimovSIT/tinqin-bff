package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.BffPartialUpdateRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PartialUpdateRoomOperationProcessor extends BaseOperationProcessor implements BffPartialUpdateRoomOperation {
    private final HotelRestExport hotelRestExport;

    public PartialUpdateRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                               Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffPartialUpdateRoomOutput> process(BffPartialUpdateRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    PartialUpdateRoomOutput hotelOutput = hotelRestExport.partialUpdateRoom(
                            input.getRoomId(), conversionService.convert(input, PartialUpdateRoomInput.class));

                    BffPartialUpdateRoomOutput output = conversionService
                            .convert(hotelOutput, BffPartialUpdateRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
