package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.Role;
import co.spring.rest.entity.dto.RoleDto;
import co.spring.rest.entity.mapper.RoleMapper;
import co.spring.rest.entity.repository.IRoleRepository;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.IRoleServ;

@Service
public class RoleServ implements IRoleServ{

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleDto findByName(String name) {
        
        Role role = iRoleRepository.findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundError("Role not found", "Role cloud not find for name in the list.", null));

        return roleMapper.toRoleDto(role);

    }



}
