package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentInput;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminEditCommentOperationProcessor extends BaseOperationProcessor implements BffAdminEditCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public AdminEditCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                              Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, BffAdminEditCommentOutput> process(BffAdminEditCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    AdminEditCommentInput commentInput = conversionService.convert(input, AdminEditCommentInput.class);

                    AdminEditCommentOutput commentsOutput = commentsRestExport
                            .adminEditComment(input.getCommentId(), commentInput);

                    BffAdminEditCommentOutput output = conversionService.convert(commentsOutput,
                            BffAdminEditCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
