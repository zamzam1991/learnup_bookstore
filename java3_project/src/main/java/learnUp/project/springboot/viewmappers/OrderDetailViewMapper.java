package learnUp.project.springboot.viewmappers;

import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.services.OrderService;
import learnUp.project.springboot.views.OrderDetailView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDetailViewMapper {

    private final BookViewMapper bookViewMapper;
    private final OrderService orderService;

    @Autowired
    public OrderDetailViewMapper(OrderService orderService, BookViewMapper bookViewMapper) {
        this.bookViewMapper = bookViewMapper;
        this.orderService = orderService;
    }

    public OrderDetailView mapToView(OrderDetail orderDetail) {
        OrderDetailView view = new OrderDetailView();
        view.setId(orderDetail.getId());
        view.setBook(bookViewMapper.mapToView(orderDetail.getBook()));
        view.setBooksCount(orderDetail.getBooksCount());
        return view;
    }

    public OrderDetail mapFromView(OrderDetailView view) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBook(bookViewMapper.mapFromView(view.getBook()));
        orderDetail.setBooksCount(view.getBooksCount());
        try {
            orderDetail.setId(view.getId());
        } catch (NullPointerException e) {
            log.warn("There is no identifier in the entity view");
        }
        return orderDetail;
    }
}
