package co.spring.rest.entity.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "customer")
public class User{

    @Id
    private long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    private BigDecimal salary;

    private boolean active;
    
}
