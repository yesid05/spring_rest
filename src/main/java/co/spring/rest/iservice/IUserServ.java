package co.spring.rest.iservice;

import java.math.BigDecimal;
import java.util.List;

import co.spring.rest.entity.dto.UserDto;

public interface IUserServ extends ICrudServ<UserDto>{

    List<UserDto> findBySalary(BigDecimal salary);

    List<UserDto> deleteBySalary(BigDecimal salary);

}
