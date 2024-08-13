package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsInput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsOperation;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetVisitorsOperationProcessor extends BaseOperationProcessor implements BffGetVisitorsOperation {
    private final HotelRestExport hotelRestExport;

    public GetVisitorsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                         Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffGetVisitorsOutput> process(BffGetVisitorsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    GetVisitorsOutput hotelOutput =
                            hotelRestExport.getVisitors(
                                    input.getStartDate(),
                                    input.getEndDate(),
                                    input.getFirstName(),
                                    input.getLastName(),
                                    input.getPhoneNumber(),
                                    input.getIdCardNumber(),
                                    input.getIdCardValidity(),
                                    input.getIdCardIssueAuthority(),
                                    input.getIdCardIssueDate(),
                                    input.getRoomNumber());

                    BffGetVisitorsOutput output = conversionService.convert(hotelOutput, BffGetVisitorsOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
