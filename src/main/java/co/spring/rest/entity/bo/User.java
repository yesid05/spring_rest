package co.spring.rest.entity.bo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails{

    @Id
    private long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    private BigDecimal salary;

    private boolean active;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }
    
}
