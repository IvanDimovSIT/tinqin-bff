package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentInput;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentOutput;
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
public class AdminEditCommentOperationProcessor extends BaseOperationProcessor implements AdminEditCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public AdminEditCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                              Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, AdminEditCommentOutput> process(AdminEditCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentInput
                            commentInput = conversionService.convert(input,
                            com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentInput.class);

                    com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentOutput commentsOutput =
                            commentsRestExport.adminEditComment(input.getCommentId(), commentInput);

                    AdminEditCommentOutput output = conversionService.convert(commentsOutput,
                            AdminEditCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
