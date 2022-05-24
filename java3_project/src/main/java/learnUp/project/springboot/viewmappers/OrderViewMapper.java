package learnUp.project.springboot.viewmappers;

import learnUp.project.springboot.entities.Book;
import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.views.OrderDetailView;
import learnUp.project.springboot.views.OrderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderViewMapper {

    private final ClientViewMapper clientViewMapper;
    private final OrderDetailViewMapper orderDetailViewMapper;

    @Autowired
    public OrderViewMapper(ClientViewMapper clientViewMapper, OrderDetailViewMapper orderDetailViewMapper) {
        this.clientViewMapper = clientViewMapper;
        this.orderDetailViewMapper = orderDetailViewMapper;
    }

    public OrderView mapToView(Order order) {
        OrderView view = new OrderView();
        view.setId(order.getId());
        view.setClient(clientViewMapper.mapToView(order.getClient()));
        Double sum = 0.0;

        for (OrderDetail detail : order.getOrderDetails())
            sum += detail.getBooksCount() * detail.getBook().getPrice();

        view.setSum(sum);
        view.setOrderDetails(order.getOrderDetails()
                            .stream()
                            .map(orderDetailViewMapper::mapToView)
                            .collect(Collectors.toList()));
        return view;
    }

    public Order mapFromView(OrderView view) {
        Order order = new Order();
        try {
            order.setId(view.getId());
        } catch (NullPointerException e) {
            log.warn("There is no identifier in the entity view");
        }
        order.setClient(clientViewMapper.mapFromView(view.getClient()));
        Double sum = 0.0;

        for (OrderDetailView detailView : view.getOrderDetails())
            sum += detailView.getBooksCount() * detailView.getBook().getPrice();

        order.setSum(sum);
        order.setOrderDetails(view.getOrderDetails()
                                .stream()
                                .map(orderDetailViewMapper::mapFromView)
                                .collect(Collectors.toList()));
        return order;
    }
}
