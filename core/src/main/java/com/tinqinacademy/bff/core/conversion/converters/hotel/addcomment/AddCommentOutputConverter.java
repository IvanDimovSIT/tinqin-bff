package com.tinqinacademy.bff.core.conversion.converters.hotel.addcomment;

import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class AddCommentOutputConverter extends BaseConverter<AddCommentOutput, BffAddCommentOutput> {
    @Override
    protected BffAddCommentOutput convertObject(
            AddCommentOutput source) {
        return BffAddCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
