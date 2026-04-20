package co.spring.rest.entity.bo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<JsonWebTokenAccess> jsonWebTokenAccesses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> permissions = new ArrayList<>();

        if(role == null)
            return permissions;

        if(role.getPermissions() == null){
            permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            return permissions;
        }

        permissions = role.getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
            .collect(Collectors.toList());

        
        permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));

        return permissions;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
}
