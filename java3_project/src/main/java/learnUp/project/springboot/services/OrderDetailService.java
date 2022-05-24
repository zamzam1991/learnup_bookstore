package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.repositories.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;

@Slf4j
@Service
public class OrderDetailService {

    private final OrderDetailRepository detailRepository;

    public OrderDetailService(OrderDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public OrderDetail createDetail(OrderDetail orderDetail) {
        return detailRepository.merge(orderDetail);
    }

    public List<OrderDetail> getDetails() {
        return detailRepository.findAll();
    }

    public OrderDetail getDetailById(Long id) {
        return detailRepository.getById(id);
    }

    public OrderDetail getDetailByOrderAndBook(Long orderId, Long bookId) {
        return detailRepository.findByOrderAndBook(orderId, bookId);
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public OrderDetail update(OrderDetail orderDetail) {
        try {
            return detailRepository.merge(orderDetail);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for detail {}", orderDetail.getId());
            throw e;
        }
    }

    public Boolean deleteOrderDetail(Long detailId) {
        detailRepository.deleteById(detailId);
        return true;
    }

}
