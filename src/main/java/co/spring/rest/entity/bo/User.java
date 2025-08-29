package co.spring.rest.entity.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record User(
    int id,
    String name,
    String lastName,
    LocalDate birthDay,
    BigDecimal salary,
    boolean active
) {}
