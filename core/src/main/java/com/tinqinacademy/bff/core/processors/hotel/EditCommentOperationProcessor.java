package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.EditCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.EditCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.editcomment.EditCommentOutput;
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
public class EditCommentOperationProcessor extends BaseOperationProcessor implements EditCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public EditCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                         Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<Errors, EditCommentOutput> process(EditCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentInput commentInput =
                            conversionService.convert(input, com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentInput.class);

                    com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput commentsOutput =
                            commentsRestExport.editComment(input.getCommentId(), commentInput);

                    EditCommentOutput output = conversionService.convert(commentsOutput, EditCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
