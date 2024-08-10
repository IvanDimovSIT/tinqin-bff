package com.tinqinacademy.bff.core.conversion.converters.system;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.api.operations.system.registervisitor.RegisterVisitorOutput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorOutputConverter extends BaseConverter<
        com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput, RegisterVisitorOutput> {
    @Override
    protected RegisterVisitorOutput convertObject(com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput source) {
        return RegisterVisitorOutput.builder()
                .build();
    }
}
