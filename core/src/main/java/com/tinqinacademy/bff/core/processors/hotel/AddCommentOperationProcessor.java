package com.tinqinacademy.bff.core.processors.hotel;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentInput;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentOperation;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput;
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
public class AddCommentOperationProcessor extends BaseOperationProcessor implements BffAddCommentOperation {
    private final CommentsRestExport commentsRestExport;
    private final HotelRestExport hotelRestExport;

    public AddCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, CommentsRestExport commentsRestExport,
                                        HotelRestExport hotelRestExport) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
        this.hotelRestExport = hotelRestExport;
    }

    private void validateRoomId(String roomId){
        com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput hotelOutput =
                hotelRestExport.getRoom(roomId);
    }

    @Override
    public Either<Errors, BffAddCommentOutput> process(BffAddCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);
                    validateRoomId(input.getRoomId());

                    AddCommentOutput commentsOutput = commentsRestExport
                            .addComment(input.getRoomId(), conversionService.convert(input, AddCommentInput.class));

                    BffAddCommentOutput output = conversionService.convert(commentsOutput, BffAddCommentOutput.class);

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
