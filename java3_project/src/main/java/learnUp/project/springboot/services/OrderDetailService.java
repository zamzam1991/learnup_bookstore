package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.repositories.OrderDetailRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderDetailService {

    private final OrderDetailRepository detailRepository;

    public OrderDetailService(OrderDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public OrderDetail createDetail(OrderDetail orderDetail) {
        return detailRepository.save(orderDetail);
    }

    public List<OrderDetail> getDetails() {
        return detailRepository.findAll();
    }

    public OrderDetail getDetailById(Long id) {
        return detailRepository.getById(id);
    }
}
