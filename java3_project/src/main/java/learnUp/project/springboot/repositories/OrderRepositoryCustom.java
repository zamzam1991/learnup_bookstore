package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Order;

public interface OrderRepositoryCustom {

    Order merge(Order order);

}
