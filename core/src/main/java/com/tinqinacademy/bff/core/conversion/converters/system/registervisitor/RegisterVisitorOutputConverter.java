package com.tinqinacademy.bff.core.conversion.converters.system.registervisitor;

import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.bff.api.operations.system.registervisitor.BffRegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorOutput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorOutputConverter extends BaseConverter<RegisterVisitorOutput, BffRegisterVisitorOutput> {
    @Override
    protected BffRegisterVisitorOutput convertObject(RegisterVisitorOutput source) {
        return BffRegisterVisitorOutput.builder()
                .build();
    }
}
