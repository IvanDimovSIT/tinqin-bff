package com.tinqinacademy.bff.api.model.visitor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class VisitorInput {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    @Size(min = 2, max = 50, message = "Between 2 and 50 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50, message = "Between 2 and 50 characters")
    private String lastName;
    @NotBlank
    @Pattern(regexp = "[0-9]{10}", message = "10 Digits")
    private String phoneNumber;
    @Pattern(regexp = "[0-9]{8,16}", message = "8-16 Digits")
    private String idCardNumber;
    private LocalDate idCardValidity;
    private String idCardIssueAuthority;
    private LocalDate idCardIssueDate;
    private LocalDate dateOfBirth;
    @NotBlank
    @UUID
    private String roomId;
}
