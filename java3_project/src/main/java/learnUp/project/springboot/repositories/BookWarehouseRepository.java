package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.BookWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookWarehouseRepository extends JpaRepository<BookWarehouse, Long> {

}
