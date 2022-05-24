package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Book;

public interface BookRepositoryCustom {

    Book merge(Book book);

}
