package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.filters.BookFilter;
import learnUp.project.springboot.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import static learnUp.project.springboot.specifications.BookSpecification.getByFilter;
import static org.springframework.data.jpa.domain.Specification.*;

@Slf4j
@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book createBook(Book book) {
        return repository.merge(book);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.getById(id);
    }

    public Book getBookByName(String name) {
        return repository.findByName(name);
    }

    public List<Book> getAllBooksByAuthorName(String name) {
        return repository.findAllByAuthorName(name);
    }

    public List<Book> getAllBooksByAuthorId(Long id) {
        return repository.findAllByAuthorId(id);
    }

    public List<Book> getAllBooksByAuthor(Author author) {
        return repository.findAllByAuthor(author);
    }

    public List<Book> getBooksBy(BookFilter filter) {
        return repository.findAll(where(getByFilter(filter)));
    }

    @Transactional
    //@CacheEvict(value = "book", key = "#book.id")
    @Lock(value = LockModeType.READ)
    public Book update(Book book) {
        try {
            return repository.merge(book);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for book {}", book.getId());
            throw e;
        }
    }

    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


}
