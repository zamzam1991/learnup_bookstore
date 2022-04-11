package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author createAuthor(Author author) {
        return repository.save(author);
    }

    public List<Author> getAuthor() {
        return repository.findAll();
    }

    public Author getAuthorById(Long id) {
        return repository.getById(id);
    }

}
