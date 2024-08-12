package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.EditCommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.GetCommentsInput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.GetCommentsOperation;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsOperation {
    private final CommentsRestExport commentsRestExport;

    public GetCommentsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                         Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, GetCommentsOutput> process(GetCommentsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);


                    com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput commentsOutput =
                            commentsRestExport.getComments(input.getRoomId());

                    GetCommentsOutput output =
                            conversionService.convert(commentsOutput, GetCommentsOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
