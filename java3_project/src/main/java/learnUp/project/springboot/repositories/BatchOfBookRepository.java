package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.BatchOfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchOfBookRepository extends JpaRepository<BatchOfBook, Long> {

}
