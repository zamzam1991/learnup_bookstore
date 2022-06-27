package learnUp.project.springboot.services;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.entities.Role;
import learnUp.project.springboot.filters.ClientFilter;
import learnUp.project.springboot.repositories.ClientRepository;
import learnUp.project.springboot.repositories.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityExistsException;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static learnUp.project.springboot.specifications.ClientSpecification.getByFilter;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository repository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(
            ClientRepository repository,
            RolesRepository rolesRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void create(Client client) {
        Client exist = repository.findByUsername(client.getUsername());
        if (exist != null) {
            throw new EntityExistsException("Client with username "
                    + client.getUsername() + " already exist");
        }
        String password = passwordEncoder.encode(client.getPassword());
        client.setPassword(password);

        Set<String> roles = client.getRoles()
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());

        Set<Role> existRoles = rolesRepository.findByRoleIn(roles);
        client.setRoles(existRoles);
        existRoles.forEach(role -> role.setClients(Set.of(client)));

        repository.save(client);
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

    @Override
    public Client loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}
