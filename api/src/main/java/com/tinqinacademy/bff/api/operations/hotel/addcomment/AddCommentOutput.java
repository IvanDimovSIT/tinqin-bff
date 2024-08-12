package com.tinqinacademy.bff.api.operations.hotel.addcomment;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class AddCommentOutput implements OperationOutput {
    private String id;
}
