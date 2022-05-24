package learnUp.project.springboot.views;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientView {

    private Long id;
    private String FIO;
    private LocalDate birthDate;
}
