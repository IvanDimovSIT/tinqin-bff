package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.AdminDeleteCommentInput;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.AdminDeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.AdminDeleteCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminDeleteCommentOperationProcessor extends BaseOperationProcessor implements AdminDeleteCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public AdminDeleteCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }


    @Override
    public Either<Errors, AdminDeleteCommentOutput> process(AdminDeleteCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput
                            commentsOutput = commentsRestExport.adminDeleteComment(input.getCommentId());


                    AdminDeleteCommentOutput output = conversionService.convert(commentsOutput,
                            AdminDeleteCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
