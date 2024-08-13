package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomInput;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOperation;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddRoomOperationProcessor extends BaseOperationProcessor implements BffAddRoomOperation {
    private final HotelRestExport hotelRestExport;

    public AddRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                     Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffAddRoomOutput> process(BffAddRoomInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);


                    AddRoomOutput hotelOutput = hotelRestExport
                            .addRoom(conversionService.convert(input, AddRoomInput.class));

                    BffAddRoomOutput output = conversionService.convert(hotelOutput, BffAddRoomOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
