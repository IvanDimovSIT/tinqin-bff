package com.tinqinacademy.bff.core.conversion.converters.hotel.addcomment;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.api.operations.hotel.addcomment.BffAddCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.addcomment.AddCommentInput;
import org.springframework.stereotype.Service;

@Service
public class AddCommentInputConverter extends BaseConverter<BffAddCommentInput, AddCommentInput> {

    @Override
    protected AddCommentInput
    convertObject(BffAddCommentInput source) {
        return AddCommentInput.builder()
                .authorId(source.getAuthorId())
                .content(source.getContent())
                .roomId(source.getRoomId())
                .build();
    }
}
