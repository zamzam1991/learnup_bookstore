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
@Table(name = "batches_of_books")
public class BatchOfBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "bookwarehouse", updatable = false, insertable = false)
    @Fetch(FetchMode.SELECT)
    private BookWarehouse bookWarehouse;

    @ManyToOne
    @JoinColumn(name = "book", updatable = false, insertable = false)
    @Fetch(FetchMode.SELECT)
    private Book book;

    @Column(name = "books_count")
    private int booksCount;
}
