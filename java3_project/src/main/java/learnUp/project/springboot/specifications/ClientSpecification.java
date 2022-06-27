package learnUp.project.springboot.specifications;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.filters.ClientFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class ClientSpecification {

    public static Specification<Client> getByFilter(ClientFilter filter) {
        return ((root, query, cb) -> {
            Predicate predicate = cb.isNotNull(root.get("id"));
            Join<Order, Client> orderJoin;

            if (filter.getFIO() != null) {
                predicate = cb.and(predicate, cb.like(root.get("FIO"), "%" + filter.getFIO() + "%"));
            }

            if (filter.getUsername() != null) {
                predicate = cb.and(predicate, cb.like(root.get("username"), "%" + filter.getUsername() + "%"));
            }

            if (filter.getBirthDate() != null) {
                predicate = cb.and(cb.lessThanOrEqualTo(root.get("birthDate"), filter.getBirthDate()));
            }

            if (filter.getOrderId() != null) {
                orderJoin = root.join("client");
                predicate = cb.and(predicate, cb.equal(orderJoin.get("orderId"), "%" + filter.getOrderId() + "%"));
            }

            return predicate;
        });
    }
}
