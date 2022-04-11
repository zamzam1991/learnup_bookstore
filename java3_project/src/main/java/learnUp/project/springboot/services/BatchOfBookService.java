package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.BatchOfBook;
import learnUp.project.springboot.repositories.BatchOfBookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BatchOfBookService {
    private final BatchOfBookRepository repository;

    public BatchOfBookService(BatchOfBookRepository repository) {
        this.repository = repository;
    }

    public BatchOfBook createBatchOfBook(BatchOfBook batchOfBook) {
        return repository.save(batchOfBook);
    }

    public List<BatchOfBook> getBatchesOfBook() {
        return repository.findAll();
    }

    public BatchOfBook getBatchOfBookById(Long id) {
        return repository.getById(id);
    }
}
