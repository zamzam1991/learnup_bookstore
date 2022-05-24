package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.OrderDetail;

public interface OrderDetailRepositoryCustom {

    OrderDetail merge(OrderDetail detail);
}
