package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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

}
