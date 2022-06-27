package learnUp.project.springboot.views;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ClientView {

    private Long id;
    private String FIO;
    private String username;
    private String password;
    private LocalDate birthDate;
    private Set<RoleView> roles;
}
