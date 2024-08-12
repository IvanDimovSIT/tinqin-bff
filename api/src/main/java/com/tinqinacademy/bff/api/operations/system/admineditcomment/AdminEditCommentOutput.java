package com.tinqinacademy.bff.api.operations.system.admineditcomment;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class AdminEditCommentOutput implements OperationOutput {
    private String id;
}
