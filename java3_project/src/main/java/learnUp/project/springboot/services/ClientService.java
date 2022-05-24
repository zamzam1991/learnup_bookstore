package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.filters.ClientFilter;
import learnUp.project.springboot.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import static learnUp.project.springboot.specifications.ClientSpecification.getByFilter;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client createClient(Client client) {
        return repository.save(client);
    }

    public List<Client> getClients() {
        return repository.findAll();
    }

    public Client getClientById(Long id) {
        return repository.getById(id);
    }

    public List<Client> getClientsBy(ClientFilter filter) {
        return repository.findAll(where(getByFilter(filter)));
    }

    public Client getClientByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    //@CacheEvict(value = "client", key = "#client.id")
    @Lock(value = LockModeType.READ)
    public Client update(Client client) {
        try {
            return repository.save(client);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for client {}", client.getId());
            throw e;
        }
    }

    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

}
