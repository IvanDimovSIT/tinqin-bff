package com.tinqinacademy.bff.core.conversion.converters.system.admineditcomment;

import com.tinqinacademy.bff.api.operations.system.admineditcomment.BffAdminEditCommentInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.admineditcomment.AdminEditCommentInput;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentInputConverter extends BaseConverter<BffAdminEditCommentInput, AdminEditCommentInput> {
    @Override
    protected AdminEditCommentInput convertObject(
            BffAdminEditCommentInput source) {
        return AdminEditCommentInput.builder()
                .commentId(source.getCommentId())
                .adminId(source.getAdminId())
                .roomId(source.getRoomId())
                .content(source.getContent())
                .build();
    }
}
