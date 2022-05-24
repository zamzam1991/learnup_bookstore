package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.filters.AuthorFilter;
import learnUp.project.springboot.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import static learnUp.project.springboot.specifications.AuthorSpecification.getByFilter;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author createAuthor(Author author) {
        return repository.save(author);
    }

    public Author getAuthorByName(String name) {
        return repository.findByName(name);
    }

    public Author getAuthorById(Long id) {
        return repository.getById(id);
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public List<Author> getAuthorsBy(AuthorFilter filter) {
        return repository.findAll(where(getByFilter(filter)));
    }

    @Transactional
    //@CacheEvict(value = "author", key = "#author.id")
    @Lock(value = LockModeType.READ)
    public Author update(Author author) {
        try {
            return repository.save(author);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for author {}", author.getId());
            throw e;
        }
    }

    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }
}
