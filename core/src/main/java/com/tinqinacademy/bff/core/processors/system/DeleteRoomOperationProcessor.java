package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomInput;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.system.deleteroom.BffDeleteRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteRoomOperationProcessor extends BaseOperationProcessor
        implements BffDeleteRoomOperation {
    private final HotelRestExport hotelRestExport;

    public DeleteRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffDeleteRoomOutput> process(BffDeleteRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    DeleteRoomOutput hotelOutput =
                            hotelRestExport.deleteRoom(input.getId());

                    BffDeleteRoomOutput output = conversionService.convert(hotelOutput, BffDeleteRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
