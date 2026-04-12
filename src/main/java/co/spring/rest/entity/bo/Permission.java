package co.spring.rest.entity.bo;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    private long id;

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> role;

}
