package learnUp.project.springboot.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NamedQuery(name = "Book", query = "select b from Book b where id = :id")
@Table(name = "books")
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author", updatable = false, insertable = false, nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    @ToString.Exclude
    private Author author;

    //private String imageUrl;// = "https://origami.kosmulski.org/img/icons/book-openclipart-67633.png";

    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @Column(name = "pages_count")
    private int pagesCount;

    @Column(name = "price", columnDefinition = "NUMERIC(8,2)")
    private Double price;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SELECT)
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SELECT)
    @ToString.Exclude
    private List<BatchOfBook> batches;




}
