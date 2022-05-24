package learnUp.project.springboot.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Fetch(FetchMode.JOIN)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Fetch(FetchMode.JOIN)
    private Book book;

    @Column(name = "count")
    private int booksCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail detail = (OrderDetail) o;
        return getBooksCount() == detail.getBooksCount() && Objects.equals(getOrder(), detail.getOrder()) && Objects.equals(getBook(), detail.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getBook(), getBooksCount());
    }
}
