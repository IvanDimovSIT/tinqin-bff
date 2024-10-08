package com.tinqinacademy.bff.core.conversion.converters.hotel.editcomment;

import com.tinqinacademy.bff.api.operations.hotel.editcomment.BffEditCommentInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.editcomment.EditCommentInput;
import org.springframework.stereotype.Component;

@Component
public class EditCommentInputConverter extends BaseConverter<
        BffEditCommentInput, EditCommentInput> {

    @Override
    protected EditCommentInput convertObject(
            BffEditCommentInput source) {
        return EditCommentInput.builder()
                .commentId(source.getCommentId())
                .content(source.getContent())
                .authorId(source.getAuthorId())
                .content(source.getContent())
                .build();
    }
}
