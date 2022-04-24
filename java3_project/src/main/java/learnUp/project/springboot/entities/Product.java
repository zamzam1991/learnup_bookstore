package learnUp.project.springboot.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedQuery(name = "Product", query = "select p from Product p where id = :id")
@Table(name = "products")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Version
    private Long version;

}
