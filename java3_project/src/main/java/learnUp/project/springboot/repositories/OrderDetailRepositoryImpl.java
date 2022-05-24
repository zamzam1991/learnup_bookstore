package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.OrderDetail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class OrderDetailRepositoryImpl implements OrderDetailRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public OrderDetail merge(OrderDetail detail) {
        return em.merge(detail);
    }
}
