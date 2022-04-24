package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from products p where p.name = ?1", nativeQuery = true)
    Product findByName(String name);

}
