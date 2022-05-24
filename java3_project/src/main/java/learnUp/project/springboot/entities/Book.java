package learnUp.project.springboot.entities;

import lombok.*;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NamedQuery(name = "Book", query = "select b from Book b where id = :id")
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author")
    @Fetch(FetchMode.JOIN)
    private Author author;

    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @Column(name = "pages_count")
    private int pagesCount;

    @Column(name = "price", columnDefinition = "NUMERIC(8,2)")
    private Double price;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    private List<BatchOfBook> batches;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getYearOfPublication() == book.getYearOfPublication() && getPagesCount() == book.getPagesCount() && Objects.equals(getName(), book.getName()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getPrice(), book.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getYearOfPublication(), getPagesCount(), getPrice());
    }
}
