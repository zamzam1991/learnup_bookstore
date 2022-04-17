package learnUp.project.springboot;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.services.AuthorService;
import learnUp.project.springboot.services.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication.class, args);

        BookService bookService = context.getBean(BookService.class);

        List<Book> books = bookService.getAllBooksByAuthorName("Джон Рональд Руэл Толкиен");
        System.out.println(books);
    }
}
