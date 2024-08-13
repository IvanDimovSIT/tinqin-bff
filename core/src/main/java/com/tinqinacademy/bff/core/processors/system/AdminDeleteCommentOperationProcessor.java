package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentInput;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminDeleteCommentOperationProcessor extends BaseOperationProcessor implements BffAdminDeleteCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public AdminDeleteCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }


    @Override
    public Either<Errors, BffAdminDeleteCommentOutput> process(BffAdminDeleteCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    AdminDeleteCommentOutput commentsOutput = commentsRestExport
                            .adminDeleteComment(input.getCommentId());


                    BffAdminDeleteCommentOutput output = conversionService.convert(commentsOutput,
                            BffAdminDeleteCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
