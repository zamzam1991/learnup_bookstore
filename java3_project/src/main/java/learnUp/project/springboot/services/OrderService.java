package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.filters.OrderFilter;
import learnUp.project.springboot.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

    public List<Order> getOrdersBy(OrderFilter filter) {
        return null;
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findAllByClientId(clientId);
    }

    public Order createOrder(Order order) {
        return orderRepository.merge(order);
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public Order update(Order order) {
        try {
            return orderRepository.merge(order);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for detail {}", order.getId());
            throw e;
        }
    }

    public Boolean deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return true;
    }

}
