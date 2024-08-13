package com.tinqinacademy.bff.core.conversion.converters.hotel.getcomments;

import com.tinqinacademy.bff.api.model.comment.CommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.BffGetCommentsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetCommentsOutputConverter extends BaseConverter<GetCommentsOutput, BffGetCommentsOutput> {
    @Override
    protected BffGetCommentsOutput convertObject(GetCommentsOutput source) {
        List<CommentOutput> comments = source.getComments()
                .stream()
                .map(comment -> CommentOutput.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .firstName(comment.getFirstName()) // TODO: set user data??
                        .lastName(comment.getLastName())
                        .lastEditedDate(comment.getLastEditedDate())
                        .lastEditedBy(comment.getLastEditedBy())
                        .publishDate(comment.getPublishDate())
                        .build())
                .toList();

        return BffGetCommentsOutput.builder()
                .comments(comments)
                .build();
    }
}
