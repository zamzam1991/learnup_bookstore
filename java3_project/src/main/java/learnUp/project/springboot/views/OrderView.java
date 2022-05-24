package learnUp.project.springboot.views;

import lombok.Data;
import java.util.List;

@Data
public class OrderView {

    private Long id;
    private ClientView client;
    private Double sum;
    private List<OrderDetailView> orderDetails;
}
