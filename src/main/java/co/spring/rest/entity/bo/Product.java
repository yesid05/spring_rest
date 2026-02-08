package co.spring.rest.entity.bo;

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
@Table(name = "product")
public class Product {
    
    @Id
    private long id;

    private String name;

    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String img;

    @Column(name = "id_category")
    private long idCategoria;

}
