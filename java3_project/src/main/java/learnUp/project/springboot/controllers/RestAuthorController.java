package learnUp.project.springboot.controllers;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.filters.AuthorFilter;
import learnUp.project.springboot.services.AuthorService;
import learnUp.project.springboot.viewmappers.AuthorViewMapper;
import learnUp.project.springboot.views.AuthorView;
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
@RequestMapping("api/v1/authors")
public class RestAuthorController {

    private final AuthorService service;
    private final AuthorViewMapper mapper;

    @Autowired
    public RestAuthorController(AuthorService service, AuthorViewMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AuthorView> getAuthors(
            @RequestParam(value = "name", required = false) String FIO
    ) {
        return service.getAuthorsBy(new AuthorFilter(FIO))
                .stream()
                .map(mapper::mapToView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{authorId}")
    public AuthorView getAuthor(@PathVariable("authorId") Long authorId) {
        return mapper.mapToView(service.getAuthorById(authorId));
    }

    @PostMapping
    public AuthorView createAuthor(@RequestBody AuthorView body) {
        if (body.getId() != null) {
            throw new EntityExistsException(
                    String.format("Author with id = %s already exist", body.getId())
            );
        }
        Author author = mapper.mapFromView(body);
        Author createdAuthor = service.createAuthor(author);
        return mapper.mapToView(createdAuthor);
    }

    @PutMapping("/{authorId}")
    public AuthorView updateAuthor(
            @PathVariable("authorId") Long authorId,
            @RequestBody AuthorView body
    ) {
        if (body.getId() == null)
            throw new EntityNotFoundException("Try to found null entity");

        if (!Objects.equals(authorId, body.getId()))
            throw new RuntimeException("Entity has bad id");

        Author author = service.getAuthorById(authorId);

        if (!author.getFIO().equals(body.getFIO()))
            author.setFIO(body.getFIO());

        Author updated = service.update(author);
        return mapper.mapToView(updated);
    }

    @DeleteMapping("/{authorId}")
    public Boolean deleteAuthor(@PathVariable("authorId") Long authorId) {
        return service.delete(authorId);
    }
}
