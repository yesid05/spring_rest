package co.spring.rest.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.JsonWebTokenAccess;
import java.util.Optional;


@Repository
public interface IJsonWebTokenAccessRepository extends JpaRepository<JsonWebTokenAccess, Long> {

    Optional<JsonWebTokenAccess> findByToken(String token);

}
