package com.tinqinacademy.bff.core.conversion.converters.system.admineditcomment;

import com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentConverter extends BaseConverter<
        com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentOutput, AdminEditCommentOutput> {
    @Override
    protected AdminEditCommentOutput convertObject(
            com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentOutput source) {
        return AdminEditCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
