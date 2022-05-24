package learnUp.project.springboot.viewmappers;

import learnUp.project.springboot.entities.Client;
import learnUp.project.springboot.views.ClientView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientViewMapper {

    public ClientView mapToView(Client client) {
        ClientView view = new ClientView();
        view.setId(client.getId());
        view.setFIO(client.getFIO());
        view.setBirthDate(client.getBirthDate());
        return view;
    }

    public Client mapFromView(ClientView view) {
        Client client = new Client();
        try {
            client.setId(view.getId());
        } catch (NullPointerException e) {
            log.warn("There is no identifier in the entity view");
        }
        client.setFIO(view.getFIO());
        client.setBirthDate(view.getBirthDate());
        return client;
    }
}
