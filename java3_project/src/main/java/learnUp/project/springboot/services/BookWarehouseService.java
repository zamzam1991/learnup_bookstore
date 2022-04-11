package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.BookWarehouse;
import learnUp.project.springboot.repositories.BookWarehouseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookWarehouseService {

    private final BookWarehouseRepository repository;

    public BookWarehouseService(BookWarehouseRepository repository) {
        this.repository = repository;
    }

    public BookWarehouse createBookWarehouse(BookWarehouse bookWarehouse) {
        return repository.save(bookWarehouse);
    }

    public List<BookWarehouse> getBookWarehouses() {
        return repository.findAll();
    }

    public BookWarehouse getBookWarehouseById(Long id) {
        return repository.getById(id);
    }


}
