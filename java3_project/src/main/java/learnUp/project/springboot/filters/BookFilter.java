package learnUp.project.springboot.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookFilter {

    private final String name;
    private final Double price;
    private final String authorName;
    private final Long orderId;

}
