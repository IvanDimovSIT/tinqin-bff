package com.tinqinacademy.bff.core.conversion.converters.hotel.getcomments;

import com.tinqinacademy.bff.api.model.comment.CommentOutput;
import com.tinqinacademy.bff.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetCommentsOutputConverter extends BaseConverter<
        com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput, GetCommentsOutput> {
    @Override
    protected GetCommentsOutput convertObject(com.tinqinacademy.comments.api.operations.hotel.getcomments.GetCommentsOutput source) {
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

        return GetCommentsOutput.builder()
                .comments(comments)
                .build();
    }
}
