package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorInput;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorOperation;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements BffRegisterVisitorOperation {
    private final HotelRestExport hotelRestExport;

    public RegisterVisitorOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                             Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffRegisterVisitorOutput> process(BffRegisterVisitorInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    RegisterVisitorOutput hotelOutput = hotelRestExport
                            .registerVisitor(conversionService.convert(input, RegisterVisitorInput.class));

                    BffRegisterVisitorOutput output = conversionService
                            .convert(hotelOutput, BffRegisterVisitorOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
