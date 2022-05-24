package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @Query(value = "select o from Order o " +
            "JOIN o.client c ON o.client=c.id " +
            "WHERE c.id = :clientId")
    List<Order> findAllByClientId(Long clientId);

    @Modifying
    @Query(value = "delete from orders o where o.id = ?1", nativeQuery = true)
    void deleteById(Long id);

}
