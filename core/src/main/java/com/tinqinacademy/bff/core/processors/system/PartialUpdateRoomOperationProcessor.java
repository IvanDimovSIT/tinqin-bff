package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.system.partialupdateroom.PartialUpdateRoomOutput;
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
public class PartialUpdateRoomOperationProcessor extends BaseOperationProcessor implements PartialUpdateRoomOperation {
    private final HotelRestExport hotelRestExport;

    public PartialUpdateRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                               Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, PartialUpdateRoomOutput> process(PartialUpdateRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomOutput hotelOutput =
                            hotelRestExport.partialUpdateRoom(input.getRoomId(), conversionService.convert(input,
                                    com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomInput.class));

                    PartialUpdateRoomOutput output = conversionService.convert(hotelOutput, PartialUpdateRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
