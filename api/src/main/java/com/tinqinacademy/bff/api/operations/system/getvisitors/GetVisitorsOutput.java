package com.tinqinacademy.bff.api.operations.system.getvisitors;


import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.model.visitor.VisitorOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetVisitorsOutput implements OperationOutput {
    private List<VisitorOutput> visitorOutputs;
}
