package com.tinqinacademy.bff.core.conversion.converters.system.admindeletecomment;

import com.tinqinacademy.bff.api.operations.system.admindeletecomment.AdminDeleteCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AdminDeleteCommentOutputConverter extends BaseConverter<
        com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput, AdminDeleteCommentOutput> {
    @Override
    protected AdminDeleteCommentOutput convertObject(com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput source) {
        return AdminDeleteCommentOutput.builder()
                .build();
    }
}
