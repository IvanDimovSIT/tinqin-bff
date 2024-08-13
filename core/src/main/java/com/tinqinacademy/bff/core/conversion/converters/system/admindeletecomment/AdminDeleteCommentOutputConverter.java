package com.tinqinacademy.bff.core.conversion.converters.system.admindeletecomment;

import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class AdminDeleteCommentOutputConverter extends BaseConverter<AdminDeleteCommentOutput, BffAdminDeleteCommentOutput> {
    @Override
    protected BffAdminDeleteCommentOutput convertObject(AdminDeleteCommentOutput source) {
        return BffAdminDeleteCommentOutput.builder()
                .build();
    }
}
