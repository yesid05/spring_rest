package co.spring.rest.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email.")
    private String email;

    @NotNull(message = "Phone number is required.")
    @NotBlank(message = "Phone number is required.")
    @Pattern(
        regexp = "^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[0-9]+)[A-Za-z0-9]+$",
        message = "The password must have the following format: Aa1"
    )
    @Size(min = 8, message = "The password must be at least 8 characters")
    private String password;

}
