package com.tinqinacademy.bff.core.conversion.converters.system.getvisitors;

import com.tinqinacademy.bff.api.model.visitor.VisitorOutput;
import com.tinqinacademy.bff.api.operations.system.getvisitors.GetVisitorsOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetVisitorsOutputConverter extends BaseConverter<
        com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput,GetVisitorsOutput> {
    @Override
    protected GetVisitorsOutput convertObject(com.tinqinacademy.hotel.api.operations.system.getvisitors.GetVisitorsOutput source) {
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

        return GetVisitorsOutput.builder()
                .visitorOutputs(visitors)
                .build();
    }
}
