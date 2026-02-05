package co.spring.rest.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @JsonIgnore
    private long id;

    private String name;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_day")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    private String birthDay;

    private String salary;

    private boolean active;

    @JsonInclude(value = Include.NON_NULL)
    private String phoneNumber;

}
