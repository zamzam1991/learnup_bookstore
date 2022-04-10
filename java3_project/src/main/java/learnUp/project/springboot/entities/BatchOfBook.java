package learnUp.project.springboot.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class BatchOfBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id")
    @Fetch(FetchMode.JOIN)
    private BookWarehouse bookWarehouse;

    @OneToOne
    @JoinColumn(name = "id")
    @Fetch(FetchMode.SUBSELECT)
    private Book book;

    private int booksCount;
}
