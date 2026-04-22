package co.spring.rest.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.spring.rest.entity.bo.JsonWebTokenRefresh;

public interface IJsonWebTokenRefreshRepository  extends JpaRepository<JsonWebTokenRefresh, Long>{}
