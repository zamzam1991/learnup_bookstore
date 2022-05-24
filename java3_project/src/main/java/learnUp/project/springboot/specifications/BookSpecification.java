package learnUp.project.springboot.specifications;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.filters.BookFilter;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class BookSpecification {

    public static Specification<Book> getByFilter(BookFilter filter) {
        return ((root, query, cb) -> {
            Predicate predicate = cb.isNotNull(root.get("id"));
            Join<Author, Book> authorJoin;
            Join<Book, OrderDetail> orderDetailJoin;
            Join<Order, OrderDetail> orderJoin;

            if (filter.getName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            if (filter.getPrice() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), filter.getPrice()));
            }

            if (filter.getAuthorName() != null) {
                authorJoin = root.join("author");
                predicate = cb.and(predicate, cb.like(authorJoin.get("FIO"), "%" + filter.getAuthorName() + "%"));
            }

            if (filter.getOrderId() != null) {
                orderDetailJoin = root.join("book");
                orderJoin = orderDetailJoin.join("order");
                predicate = cb.and(predicate, cb.equal(orderJoin.get("id"), filter.getOrderId()));
            }

            return predicate;
        });
    }
}
