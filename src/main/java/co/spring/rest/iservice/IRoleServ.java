package co.spring.rest.iservice;

import co.spring.rest.entity.dto.RoleDto;

public interface IRoleServ {

    RoleDto findByName(String name);

}
