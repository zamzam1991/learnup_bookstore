package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Book;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class BookRepositoryImpl implements BookRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Book merge(Book book) {
        return em.merge(book);
    }
}
