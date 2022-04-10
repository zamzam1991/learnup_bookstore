package learnUp.project.springboot.entities;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id")
    @Fetch(FetchMode.JOIN)
    private Order order;

    @OneToMany
    @JoinColumn(name = "id")
    @Fetch(FetchMode.JOIN)
    Book book;

    @Column
    private int booksCount;

    @Column
    private double price;
}
