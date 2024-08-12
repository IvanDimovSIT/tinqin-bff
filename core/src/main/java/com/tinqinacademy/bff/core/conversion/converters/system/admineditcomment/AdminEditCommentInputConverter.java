package com.tinqinacademy.bff.core.conversion.converters.system.admineditcomment;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentInput;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentInputConverter extends BaseConverter<
        com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentInput, AdminEditCommentInput> {
    @Override
    protected AdminEditCommentInput convertObject(
            com.tinqinacademy.bff.api.operations.system.admineditcomment.AdminEditCommentInput source) {
        return AdminEditCommentInput.builder()
                .commentId(source.getCommentId())
                .adminId(source.getAdminId())
                .roomId(source.getRoomId())
                .content(source.getContent())
                .build();
    }
}
