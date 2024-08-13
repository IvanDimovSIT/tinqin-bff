package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.BffCheckAvailableRoomsInput;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.BffCheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.BffCheckAvailableRoomsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckAvailableRoomsOperationProcessor extends BaseOperationProcessor implements BffCheckAvailableRoomsOperation {
    private final HotelRestExport hotelRestExport;

    public CheckAvailableRoomsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                 Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<Errors, BffCheckAvailableRoomsOutput> process(BffCheckAvailableRoomsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    CheckAvailableRoomsOutput
                            hotelOutput = hotelRestExport.checkAvailableRooms(
                            input.getStartDate(),
                            input.getEndDate(),
                            input.getBedCount(),
                            input.getBedSize() == null ? null : input.getBedSize().toString(),
                            input.getBathroomType() == null ? null : input.getBathroomType().toString());

                    BffCheckAvailableRoomsOutput output = conversionService.convert(
                            hotelOutput, BffCheckAvailableRoomsOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
