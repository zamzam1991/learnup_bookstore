package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Product;
import learnUp.project.springboot.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product getProductByName(String name) {
        return repository.findByName(name);
    }

    @Cacheable(value = "product")
    public Product getProductById(Long id) {
        return repository.getById(id);
    }

    @Transactional
    //@CacheEvict(value = "product", key = "#product.id")
    @Lock(value = LockModeType.READ)
    public Product update(Product product) {
        try {
            return repository.save(product);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for product {}", product.getId());
            throw e;
        }
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}
