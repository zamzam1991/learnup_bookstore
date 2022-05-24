package learnUp.project.springboot.viewmappers;

import learnUp.project.springboot.entities.Author;
import learnUp.project.springboot.views.AuthorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorViewMapper {

    public AuthorView mapToView(Author author) {
        AuthorView view = new AuthorView();
        view.setId(author.getId());
        view.setFIO(author.getFIO());
        return view;
    }

    public Author mapFromView(AuthorView view) {
        Author author = new Author();
        try {
            author.setId(view.getId());
        } catch (NullPointerException e) {
            log.warn("There is no identifier in the entity view");
        }
        author.setFIO(view.getFIO());
        return author;
    }

}
