package learnUp.project.springboot.controllers;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.entities.Role;
import learnUp.project.springboot.filters.ClientFilter;
import learnUp.project.springboot.services.ClientService;
import learnUp.project.springboot.viewmappers.ClientViewMapper;
import learnUp.project.springboot.views.ClientView;
import learnUp.project.springboot.views.RoleView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/clients")
public class RestClientController {

    private final ClientService service;
    private final ClientViewMapper mapper;

    @Autowired
    public RestClientController(ClientService service, ClientViewMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ClientView> getClients(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(value = "orderId", required = false) Long orderId,
            @RequestParam(value = "username", required = false) String username
    ) {
        return service.getClientsBy(new ClientFilter(name, username, birthDate, orderId))
                .stream()
                .map(mapper::mapToView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{clientId}")
    public ClientView getClient(@PathVariable("clientId") Long clientId) {
        return mapper.mapToView(service.getClientById(clientId));
    }

    @PostMapping
    public Boolean createClient(@RequestBody ClientView body) {
        if (body.getId() != null) {
            throw new EntityExistsException(
                    String.format("Client with id = %s already exist", body.getId())
            );
        }

        Client entity = new Client();
        entity.setFIO(body.getFIO());
        entity.setBirthDate(body.getBirthDate());
        entity.setUsername(body.getUsername());
        entity.setPassword(body.getPassword());
        entity.setRoles(
                body.getRoles()
                        .stream()
                        .map(RoleView::getRole)
                        .map(Role::new)
                        .collect(Collectors.toSet())
        );
        service.create(entity);
        return true;
    }

    @PutMapping("/{clientId}")
    public ClientView updateClient(
            @PathVariable("clientId") Long clientId,
            @RequestBody ClientView body
    ) {
        if (body.getId() == null)
            throw new EntityNotFoundException("Try to found null entity");

        if (!Objects.equals(clientId, body.getId()))
            throw new RuntimeException("Entity has bad id");

        Client client = service.getClientById(clientId);

        if (!client.getFIO().equals(body.getFIO()))
            client.setFIO(body.getFIO());

        if (!client.getBirthDate().equals(body.getBirthDate()))
            client.setBirthDate(body.getBirthDate());

        Client updated = service.update(client);
        return mapper.mapToView(updated);
    }

    @DeleteMapping("/{clientId}")
    public Boolean deleteClient(@PathVariable("clientId") Long clientId) {
        return service.delete(clientId);
    }
}
