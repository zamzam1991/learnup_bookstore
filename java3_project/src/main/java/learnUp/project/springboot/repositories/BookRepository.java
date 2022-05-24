package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @Query(value = "select b from Book b " +
            "JOIN b.author a ON b.author=a.id " +
            "WHERE a.FIO = :name"
    )
    List<Book> findAllByAuthorName(String name);

    @Query(value = "select * from books b inner join authors a ON b.author=a.id where a.id = ?1", nativeQuery = true)
    List<Book> findAllByAuthor(Author author);

    @Query(value = "select * from books b inner join authors a ON b.author=a.id where a.id = ?1", nativeQuery = true)
    List<Book> findAllByAuthorId(Long id);

    @Query(value = "select * from books b where b.name = ?1", nativeQuery = true)
    Book findByName(String name);

    List<Book> findAllByNameContainsAndPriceLessThan(String name, Double price);

    List<Book> findAll(Specification<Book> specification);

    @Modifying
    @Query(value = "delete from books b where b.id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
