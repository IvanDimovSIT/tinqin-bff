package com.tinqinacademy.bff.api.operations.hotel.editcomment;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class EditCommentOutput implements OperationOutput {
    private String id;
}
