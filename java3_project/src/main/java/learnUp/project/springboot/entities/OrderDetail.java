package learnUp.project.springboot.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order", updatable = false, insertable = false)
    @Fetch(FetchMode.SELECT)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    @Fetch(FetchMode.SELECT)
    Book book;

    @Column(name = "count")
    private int booksCount;

    @Column(columnDefinition = "NUMERIC(8,2)")
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetail that = (OrderDetail) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
