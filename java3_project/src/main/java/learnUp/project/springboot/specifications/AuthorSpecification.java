package learnUp.project.springboot.specifications;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.filters.AuthorFilter;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;

public class AuthorSpecification {

    public static Specification<Author> getByFilter(AuthorFilter filter) {
        return ((root, query, cb) -> {
            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getFIO() != null) {
                predicate = cb.and(predicate, cb.like(root.get("FIO"), "%" + filter.getFIO() + "%"));
            }

            return predicate;
        });
    }
}
