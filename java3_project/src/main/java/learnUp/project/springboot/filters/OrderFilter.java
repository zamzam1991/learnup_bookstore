package learnUp.project.springboot.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderFilter {
    private final String bookName;
    private final Long clientId;
    private final String clientName;
    private final Double priceLessThan;
    private final Double priceMoreThan;
}
