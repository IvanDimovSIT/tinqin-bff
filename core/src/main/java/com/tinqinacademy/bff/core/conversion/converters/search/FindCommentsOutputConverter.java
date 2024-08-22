package com.tinqinacademy.bff.core.conversion.converters.search;

import com.tinqinacademy.bff.api.operations.search.findcomments.BffFindCommentsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import org.springframework.stereotype.Component;

@Component
public class FindCommentsOutputConverter extends BaseConverter<FindCommentsOutput, BffFindCommentsOutput> {
    @Override
    protected BffFindCommentsOutput convertObject(FindCommentsOutput source) {
        return BffFindCommentsOutput.builder()
                .commentIds(source.getCommentIds())
                .build();
    }
}
