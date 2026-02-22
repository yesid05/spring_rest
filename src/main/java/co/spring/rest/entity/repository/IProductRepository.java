package co.spring.rest.entity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable page);
    
}
