package learnUp.project.springboot;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.entities.Product;
import learnUp.project.springboot.services.AuthorService;
import learnUp.project.springboot.services.BookService;
import learnUp.project.springboot.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;

@Slf4j
@EnableCaching
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication.class, args);

        BookService bookService = context.getBean(BookService.class);

//        List<Book> books = bookService.getAllBooksByAuthorName("Джон Рональд Руэл Толкиен");
//        System.out.println(books);

//        AuthorService authorService = context.getBean(AuthorService.class);
//        Author author = authorService.getAuthorById(1L);
//        System.out.println(author);

        ProductService productService = context.getBean(ProductService.class);
        Product product = productService.createProduct(new Product("Milk"));

        product.setName("Milkshake");

        Product product1 = productService.update(product);

        log.info("New product - {}", product1);
    }
}
