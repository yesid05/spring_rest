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

    public static final String CREATE = "CREATE";

    public static final String READ = "READ";

    public static final String UPDATE = "UPDATE";

    public static final String DELETE = "DELETE";

    @Id
    private long id;

    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> role;

}
