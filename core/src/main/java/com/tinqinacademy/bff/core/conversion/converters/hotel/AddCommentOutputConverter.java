package com.tinqinacademy.bff.core.conversion.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.addcomment.AddCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AddCommentOutputConverter extends BaseConverter<com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput,
        AddCommentOutput> {
    @Override
    protected AddCommentOutput convertObject(
            com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentOutput source) {
        return AddCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
