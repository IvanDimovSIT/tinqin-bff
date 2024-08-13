package com.tinqinacademy.bff.core.conversion.converters.system.registervisitor;


import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.visitor.VisitorInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorInputConverter extends BaseConverter<BffRegisterVisitorInput, RegisterVisitorInput> {
    @Override
    protected com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput convertObject(BffRegisterVisitorInput source) {
        var visitorInput = source.getVisitorInputs()
                .stream()
                .map(visitor -> VisitorInput.builder()
                        .startDate(visitor.getStartDate())
                        .endDate(visitor.getEndDate())
                        .roomId(visitor.getRoomId())
                        .firstName(visitor.getFirstName())
                        .lastName(visitor.getLastName())
                        .dateOfBirth(visitor.getDateOfBirth())
                        .phoneNumber(visitor.getPhoneNumber())
                        .idCardIssueAuthority(visitor.getIdCardIssueAuthority())
                        .idCardIssueDate(visitor.getIdCardIssueDate())
                        .idCardNumber(visitor.getIdCardNumber())
                        .idCardValidity(visitor.getIdCardValidity())
                        .build())
                .toList();


        return com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput.builder()
                .visitorInputs(visitorInput)
                .build();
    }
}
