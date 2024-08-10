package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.RegisterVisitorOperation;
import com.tinqinacademy.bff.api.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.bff.api.operations.system.updateroom.UpdateRoomOutput;
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
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitorOperation {
    private final HotelRestExport hotelRestExport;

    public RegisterVisitorOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                             Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput hotelOutput =
                            hotelRestExport.registerVisitor(conversionService.convert(input,
                                    com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput.class));

                    RegisterVisitorOutput output = conversionService.convert(hotelOutput, RegisterVisitorOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
