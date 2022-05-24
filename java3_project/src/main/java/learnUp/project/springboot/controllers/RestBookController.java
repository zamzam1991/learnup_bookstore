package learnUp.project.springboot.controllers;

import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.services.AuthorService;
import learnUp.project.springboot.filters.BookFilter;
import learnUp.project.springboot.services.BookService;
import learnUp.project.springboot.viewmappers.AuthorViewMapper;
import learnUp.project.springboot.viewmappers.BookViewMapper;
import learnUp.project.springboot.views.BookView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/books")
public class RestBookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookViewMapper mapper;
    private final AuthorViewMapper authorViewMapper;

    @Autowired
    public RestBookController(BookService bookService, AuthorService authorService, BookViewMapper mapper, AuthorViewMapper authorViewMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.mapper = mapper;
        this.authorViewMapper = authorViewMapper;
    }

    @GetMapping
    public List<BookView> getBooks(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "author", required = false) String fio,
            @RequestParam(value = "order", required = false) Long orderId
    ) {
        return bookService.getBooksBy(new BookFilter(name, price, fio, orderId))
                .stream()
                .map(mapper::mapToView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookView getBook(@PathVariable("bookId") Long bookId) {
        return mapper.mapToView(bookService.getBookById(bookId));
    }

    @PostMapping
    public BookView createBook(@RequestBody BookView body) {
        if (body.getId() != null) {
            throw new EntityExistsException(
                    String.format("Book with id = %s already exist", body.getId())
            );
        }
        if (authorService.getAuthorByName(body.getAuthor().getFIO()) == null) {
            authorService.createAuthor(authorViewMapper.mapFromView(body.getAuthor()));
        }
        Book book = mapper.mapFromView(body);
        Book createdBook = bookService.createBook(book);
        return mapper.mapToView(createdBook);
    }

    @PutMapping("/{bookId}")
    public BookView updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody BookView body
    ) {
        if (body.getId() == null)
            throw new EntityNotFoundException("Try to found null entity");

        if (!Objects.equals(bookId, body.getId()))
            throw new RuntimeException("Entity has bad id");

        Book book = bookService.getBookById(bookId);

        if (!book.getName().equals(body.getName()))
            book.setName(body.getName());

        if (!book.getAuthor().getFIO().equals(body.getAuthor().getFIO()))
            book.setAuthor(authorViewMapper.mapFromView(body.getAuthor()));

        if (book.getYearOfPublication() != body.getYearOfPublication())
            book.setYearOfPublication(body.getYearOfPublication());

        if (book.getPagesCount() != body.getPagesCount())
            book.setPagesCount(body.getPagesCount());

        if (book.getPrice() != body.getPrice())
            book.setPrice(body.getPrice());

        Book updated = bookService.update(book);
        return mapper.mapToView(updated);
    }

    @DeleteMapping("/{bookId}")
    public Boolean deleteBook(@PathVariable("bookId") Long bookId) {
        return bookService.delete(bookId);
    }

}
