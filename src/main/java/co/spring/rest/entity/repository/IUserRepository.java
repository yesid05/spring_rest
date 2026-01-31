package co.spring.rest.entity.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

    List<User> findBySalary(BigDecimal salary);

    List<User> deleteBySalary(BigDecimal salary);

}