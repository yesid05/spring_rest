package co.spring.rest.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class UserDto {

    //@JsonIgnore
    private long id;

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 10, message = "The name must be between 3 and 10 characters.")
    private String name;

    @NotNull(message = "Last name is required.")
    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 10, message = "The last name must be between 3 and 30 characters.")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Birth day is required")
    @Pattern(
        regexp = "\\b\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\b",
        message = "The birth day must have the following format: yyyy-MM-dd"
    )    
    @JsonProperty("birth_day")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    private String birthDay;

    @NotNull(message = "Salary is required.")
    @NotBlank(message = "Salary is required.")
    @Pattern(
        regexp = "^\\$\\d+(\\,\\d{1,2})?$",
        message = "The salary must have the following format: $#.##"
    )
    private String salary;

    @NotNull(message = "Active is required.")
    private boolean active;

    @NotNull(message = "Phone number is required.")
    @NotBlank(message = "Phone number is required.")
    @Pattern(
        regexp = "^3\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{3}$",
        message = "The phone number must have the following format: 3## ## ## ###"
    )
    @JsonInclude(value = Include.NON_NULL)
    @JsonProperty("phone_number")
    private String phoneNumber;

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
