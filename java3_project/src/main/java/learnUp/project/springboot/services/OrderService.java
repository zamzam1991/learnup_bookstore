package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createPost(Order order) {
        return repository.save(order);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.getById(id);
    }

}
