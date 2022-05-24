package learnUp.project.springboot.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClientFilter {
    public String FIO;
    public LocalDate birthDate;
    public Long orderId;
}
