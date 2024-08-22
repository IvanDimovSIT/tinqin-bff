package com.tinqinacademy.bff.api.operations.search.findcomments;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BffFindCommentsOutput implements OperationOutput {
    private List<String> commentIds;
}
