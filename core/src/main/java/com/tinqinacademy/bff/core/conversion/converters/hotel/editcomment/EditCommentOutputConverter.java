package com.tinqinacademy.bff.core.conversion.converters.hotel.editcomment;

import com.tinqinacademy.bff.api.operations.hotel.editcomment.EditCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class EditCommentOutputConverter extends BaseConverter<
        com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput,EditCommentOutput> {
    @Override
    protected EditCommentOutput convertObject(
            com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentOutput source) {
        return EditCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
