package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailRepositoryCustom {

    @NotNull
    List<OrderDetail> findAll();

    @Modifying
    @Query(value = "delete from order_details od where od.id = ?1", nativeQuery = true)
    void deleteById(Long id);

    @Query(value = "select * from order_details od where od.order = ?1 and od.book = ?2", nativeQuery = true)
    OrderDetail findByOrderAndBook(Long orderId, Long bookId);

}
