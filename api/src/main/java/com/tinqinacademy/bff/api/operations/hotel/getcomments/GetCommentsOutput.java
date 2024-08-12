package com.tinqinacademy.bff.api.operations.hotel.getcomments;

import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.model.comment.CommentOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class GetCommentsOutput implements OperationOutput {
    List<CommentOutput> comments;
}
