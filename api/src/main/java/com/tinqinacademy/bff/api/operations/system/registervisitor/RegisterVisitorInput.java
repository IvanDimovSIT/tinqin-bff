package com.tinqinacademy.bff.api.operations.system.registervisitor;

import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.tinqinacademy.bff.api.model.visitor.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorInput implements OperationInput {
    @NotNull
    private List<@Valid VisitorInput> visitorInputs;
}
