package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.base.OperationProcessor;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.AddCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.AddCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.AddCommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.checkavailablerooms.CheckAvailableRoomsOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddCommentOperationProcessor extends BaseOperationProcessor implements AddCommentOperation {
    private final CommentsRestExport commentsRestExport;

    public AddCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
    }


    @Override
    public Either<Errors, AddCommentOutput> process(AddCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput commentsOutput =
                            commentsRestExport.addComment(input.getRoomId(), conversionService.convert(input,
                                    com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentInput.class));

                    AddCommentOutput output = conversionService.convert(commentsOutput, AddCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
