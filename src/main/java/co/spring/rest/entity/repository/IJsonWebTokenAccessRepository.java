package co.spring.rest.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.JsonWebTokenAccess;

@Repository
public interface IJsonWebTokenAccessRepository extends JpaRepository<JsonWebTokenAccess, Long> {

}
