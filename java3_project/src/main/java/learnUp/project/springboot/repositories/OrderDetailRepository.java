package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @NotNull
    public List<OrderDetail> findAll();

}
