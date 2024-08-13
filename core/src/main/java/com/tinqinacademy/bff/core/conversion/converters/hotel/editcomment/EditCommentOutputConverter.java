package com.tinqinacademy.bff.core.conversion.converters.hotel.editcomment;

import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class EditCommentOutputConverter extends BaseConverter<EditCommentOutput, BffEditCommentOutput> {
    @Override
    protected BffEditCommentOutput convertObject(EditCommentOutput source) {
        return BffEditCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
