package co.spring.rest.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByNameIgnoreCase(String name);

}
