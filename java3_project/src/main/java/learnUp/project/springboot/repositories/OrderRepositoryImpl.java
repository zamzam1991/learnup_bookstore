package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Order;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Order merge(Order order) {
        return em.merge(order);
    }

}
