package learnUp.project.springboot.viewmappers;

import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.views.BookView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookViewMapper {

    private final AuthorViewMapper authorViewMapper;

    @Autowired
    public BookViewMapper(AuthorViewMapper authorViewMapper) {
        this.authorViewMapper = authorViewMapper;
    }


    public BookView mapToView(Book book) {
        BookView view = new BookView();
        view.setId(book.getId());
        view.setName(book.getName());
        view.setAuthor(authorViewMapper.mapToView(book.getAuthor()));
        view.setYearOfPublication(book.getYearOfPublication());
        view.setPagesCount(book.getPagesCount());
        view.setPrice(book.getPrice());
        return view;
    }

    public Book mapFromView(BookView view) {
        Book book = new Book();
        try {
            book.setId(view.getId());
        } catch (NullPointerException e) {
            log.warn("There is no identifier in the entity view");
        }
        book.setName(view.getName());
        book.setAuthor(authorViewMapper.mapFromView(view.getAuthor()));
        book.setYearOfPublication(view.getYearOfPublication());
        book.setPagesCount(view.getPagesCount());
        book.setPrice(view.getPrice());
        return book;
    }
}
