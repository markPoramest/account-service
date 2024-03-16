package com.example.accountservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountDTO  {
    @NotBlank(message = "First name cannot be null")
    private String firstName;
    @NotBlank(message = "Last name cannot be null")
    private String lastName;
    @NotBlank(message = "Email cannot be null")
    private String email;
    @NotBlank(message = "Date of birth cannot be null")
    private String dateOfBirth;
    @NotBlank(message = "Id card number cannot be null")
    private String idCardNo;
}
