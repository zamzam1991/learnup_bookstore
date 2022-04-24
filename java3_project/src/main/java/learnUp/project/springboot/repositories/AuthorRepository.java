package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select * from authors a where a.fio = ?1", nativeQuery = true)
    Author findByName(String name);

    @Query(value = "from Author a join fetch a.books where a.id = :id")
    Author getById(Long id);

    List<Author> findAll();
}
