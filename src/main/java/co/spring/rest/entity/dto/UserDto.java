package co.spring.rest.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private long id;

    private String name;

    private String lastName;

    private String birthDay;

    private String salary;

    private boolean active;    

}
