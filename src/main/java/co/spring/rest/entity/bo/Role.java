package co.spring.rest.entity.bo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "role")
public class Role {

    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

    public static final String ROLE_OPERATOR = "OPERATOR";

    public static final String ROLE_CUSTOMER = "CUSTOMER";

    @Id
    private long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "id_role"),
        inverseJoinColumns = @JoinColumn(name = "id_permission")
    )
    private List<Permission> permissions;

}
