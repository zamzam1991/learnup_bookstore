package learnUp.project.springboot.specifications;

import learnUp.project.springboot.entities.*;
import learnUp.project.springboot.filters.OrderFilter;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class OrderSpecification {

    public static Specification<Order> getByFilter(OrderFilter filter) {
        return ((root, query, cb) -> {
            Predicate predicate = cb.isNotNull(root.get("id"));
            Join<OrderDetail, Order> detailJoin;
            Join<Book, OrderDetail> bookJoin;
            Join<Client, Order> clientJoin;

            if (filter.getBookName() != null) {
                detailJoin = root.join("order");
                bookJoin = detailJoin.join("name");
                predicate = cb.and(predicate, cb.equal(bookJoin.get("name"), filter.getBookName()));
            }

            if (filter.getClientId() != null) {
                clientJoin = root.join("client");
                predicate = cb.and(predicate, cb.equal(clientJoin.get("id"), filter.getClientId()));
            }

            if (filter.getClientId() != null) {
                clientJoin = root.join("client");
                predicate = cb.and(predicate, cb.equal(clientJoin.get("fio"), filter.getClientName()));
            }

            if (filter.getPriceLessThan() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), filter.getPriceLessThan()));
            }

            if (filter.getPriceMoreThan() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), filter.getPriceMoreThan()));
            }

            return predicate;
        });
    }
}
