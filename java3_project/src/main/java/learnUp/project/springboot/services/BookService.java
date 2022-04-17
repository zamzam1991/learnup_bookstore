package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.repositories.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book createBook(Book book) {
        return repository.save(book);
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

}
