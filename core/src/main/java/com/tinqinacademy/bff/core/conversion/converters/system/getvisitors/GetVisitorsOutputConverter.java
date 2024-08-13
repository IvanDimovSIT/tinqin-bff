package com.tinqinacademy.bff.core.conversion.converters.system.getvisitors;

import com.tinqinacademy.bff.api.model.visitor.VisitorOutput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.BffGetVisitorsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetVisitorsOutputConverter extends BaseConverter<GetVisitorsOutput, BffGetVisitorsOutput> {
    @Override
    protected BffGetVisitorsOutput convertObject(GetVisitorsOutput source) {
        List<VisitorOutput> visitors = source.getVisitorOutputs().stream()
                .map(visitor -> VisitorOutput.builder()
                        .roomId(visitor.getRoomId())
                        .startDate(visitor.getStartDate())
                        .endDate(visitor.getEndDate())
                        .firstName(visitor.getFirstName())
                        .lastName(visitor.getLastName())
                        .idCardIssueAuthority(visitor.getIdCardIssueAuthority())
                        .idCardValidity(visitor.getIdCardValidity())
                        .idCardIssueDate(visitor.getIdCardIssueDate())
                        .idCardNumber(visitor.getIdCardNumber())
                        .phoneNumber(visitor.getPhoneNumber())
                        .build())
                .toList();

        return BffGetVisitorsOutput.builder()
                .visitorOutputs(visitors)
                .build();
    }
}
