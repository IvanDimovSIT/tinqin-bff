package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsInput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsOperation;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements BffGetCommentsOperation {
    private final CommentsRestExport commentsRestExport;

    public GetCommentsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                         Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, BffGetCommentsOutput> process(BffGetCommentsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);


                    GetCommentsOutput commentsOutput =
                            commentsRestExport.getComments(input.getRoomId());

                    BffGetCommentsOutput output =
                            conversionService.convert(commentsOutput, BffGetCommentsOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
