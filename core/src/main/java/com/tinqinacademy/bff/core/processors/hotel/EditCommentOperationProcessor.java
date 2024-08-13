package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EditCommentOperationProcessor extends BaseOperationProcessor implements BffEditCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public EditCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                         Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, BffEditCommentOutput> process(BffEditCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    EditCommentInput commentInput = conversionService.convert(input, EditCommentInput.class);

                    EditCommentOutput commentsOutput = commentsRestExport
                            .editComment(input.getCommentId(), commentInput);

                    BffEditCommentOutput output = conversionService.convert(commentsOutput, BffEditCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
