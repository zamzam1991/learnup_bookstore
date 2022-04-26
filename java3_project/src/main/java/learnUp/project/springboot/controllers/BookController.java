package learnUp.project.springboot.controllers;

import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {

    //http://localhost:8080
    private BookService service;

    @Autowired
    public void setService(BookService service) {
        this.service = service;
    }

    @GetMapping("/store")
    public String store(Model model) {
        return "store";
    }

    @GetMapping("/catalog")
    public String books(Model model) {
        List<Book> books = service.getBooks();
        model.addAttribute("books", books);
        return "books";
    }

}
