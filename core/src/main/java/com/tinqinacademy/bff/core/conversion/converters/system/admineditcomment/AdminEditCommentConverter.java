package com.tinqinacademy.bff.core.conversion.converters.system.admineditcomment;

import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentConverter extends BaseConverter<AdminEditCommentOutput, BffAdminEditCommentOutput> {
    @Override
    protected BffAdminEditCommentOutput convertObject(AdminEditCommentOutput source) {
        return BffAdminEditCommentOutput.builder()
                .id(source.getId())
                .build();
    }
}
