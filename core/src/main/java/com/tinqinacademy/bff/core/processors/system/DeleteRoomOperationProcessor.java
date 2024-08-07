package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.system.deleteroom.DeleteRoomOutput;
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
public class DeleteRoomOperationProcessor extends BaseOperationProcessor
        implements DeleteRoomOperation {
    private final HotelRestExport hotelRestExport;

    public DeleteRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, DeleteRoomOutput> process(DeleteRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput hotelOutput =
                            hotelRestExport.deleteRoom(input.getId());

                    DeleteRoomOutput output = conversionService.convert(hotelOutput, DeleteRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
