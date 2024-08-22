package com.tinqinacademy.bff.core.processors.search;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsInput;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOperation;
import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOutput;
import com.tinqinacademy.bff.api.operations.system.addroom.BffAddRoomOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import com.tinqinacademy.search.restexport.SearchRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BffFindCommentsOperationProcessor extends BaseOperationProcessor implements BffFindCommentsOperation {
    private final SearchRestExport searchRestExport;

    public BffFindCommentsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                             Validator validator, SearchRestExport searchRestExport) {
        super(conversionService, errorMapper, validator);
        this.searchRestExport = searchRestExport;
    }

    @Override
    public Either<Errors, BffFindCommentsOutput> process(BffFindCommentsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    FindCommentsOutput findCommentsOutput = searchRestExport.findComments(input.getWord());

                    BffFindCommentsOutput output = conversionService.convert(
                            findCommentsOutput, BffFindCommentsOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
