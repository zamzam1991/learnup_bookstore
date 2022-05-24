package learnUp.project.springboot.controllers;

import learnUp.project.springboot.entities.Order;
import learnUp.project.springboot.entities.OrderDetail;
import learnUp.project.springboot.filters.OrderFilter;
import learnUp.project.springboot.services.BookService;
import learnUp.project.springboot.services.OrderDetailService;
import learnUp.project.springboot.services.OrderService;
import learnUp.project.springboot.viewmappers.OrderDetailViewMapper;
import learnUp.project.springboot.viewmappers.OrderViewMapper;
import learnUp.project.springboot.views.OrderDetailView;
import learnUp.project.springboot.views.OrderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
public class RestOrderController {

    private final OrderService service;
    private final OrderDetailService detailService;
    private final OrderViewMapper mapper;
    private final OrderDetailViewMapper detailViewMapper;
    private final BookService bookService;

    @Autowired
    public RestOrderController(
            OrderService service,
           OrderDetailService detailService,
           OrderViewMapper mapper,
           OrderDetailViewMapper detailViewMapper,
           BookService bookService
    ) {
        this.service = service;
        this.detailService = detailService;
        this.mapper = mapper;
        this.detailViewMapper = detailViewMapper;
        this.bookService = bookService;
    }

    @GetMapping
    public List<OrderView> getOrders(
            @RequestParam(value = "bookName", required = false) String bookName,
            @RequestParam(value = "clientId", required = false) Long clientId,
            @RequestParam(value = "clientName", required = false) String clientName,
            @RequestParam(value = "lessThan", required = false) Double priceLessThan,
            @RequestParam(value = "moreThan", required = false) Double priceMoreThan
    ) {
        return service.getOrdersBy(new OrderFilter(bookName, clientId, clientName, priceLessThan, priceMoreThan))
                .stream()
                .map(mapper::mapToView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public OrderView getOrder(
            @PathVariable("orderId") Long orderId
    ) {
        return mapper.mapToView(service.getOrderById(orderId));
    }

    @GetMapping("/byClientId/{clientId}")
    public List<OrderView> getOrderByClient(
            @PathVariable("clientId") Long clientId
    ) {
        return service.getOrdersByClientId(clientId)
                .stream()
                .map(mapper::mapToView)
                .collect(Collectors.toList());
    }

    @PostMapping
    public OrderView createOrder(
            @RequestBody OrderView body
    ) {
        if (body.getId() != null) {
            throw new EntityExistsException(
                    String.format("Order with id = %s already exist", body.getId())
            );
        }
        Order order = mapper.mapFromView(body);
        Order createdOrder = service.createOrder(order);
        Set<OrderDetail> detailsSet = Collections.newSetFromMap(new HashMap<>());
        detailsSet.addAll(createdOrder.getOrderDetails());
        for (OrderDetail orderDetail : detailsSet) {
            orderDetail.setOrder(createdOrder);
            detailService.update(orderDetail);
        }
        return mapper.mapToView(createdOrder);
    }

    @PostMapping("/addDetail/{orderId}")
    public OrderView addDetail(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderDetailView body
    ) {
        OrderDetail detail = detailViewMapper.mapFromView(body);
        Order order = service.getOrderById(orderId);
        order.setSum(order.getSum() + (detail.getBooksCount() * detail.getBook().getPrice()));
        detail.setOrder(order);
        detailService.createDetail(detail);
        return mapper.mapToView(service.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/addToDetail")
    public OrderView addToDetail(
            @PathVariable ("orderId") Long orderId,
            @RequestBody OrderDetailView body
    ) {
        OrderDetail detail = detailService.getDetailByOrderAndBook(orderId, (bookService.getBookByName(body.getBook().getName())).getId());
        Order order = service.getOrderById(orderId);
        detail.setBooksCount(detail.getBooksCount() + body.getBooksCount());
        order.setSum(order.getSum() + (body.getBooksCount() * body.getBook().getPrice()));
        detailService.update(detail);
        return mapper.mapToView(service.update(order));
    }

    @PutMapping("/{orderId}/addToDetail")
    public OrderView removeToDetail(
            @PathVariable ("orderId") Long orderId,
            @RequestBody OrderDetailView body
    ) {
        OrderDetail detail = detailService.getDetailByOrderAndBook(orderId, (bookService.getBookByName(body.getBook().getName())).getId());
        Order order = service.getOrderById(orderId);
        detail.setBooksCount(detail.getBooksCount() - body.getBooksCount());
        order.setSum(order.getSum() - (body.getBooksCount() * body.getBook().getPrice()));
        detailService.update(detail);
        return mapper.mapToView(service.update(order));
    }

    @DeleteMapping("/{orderId}")
    public Boolean deleteOrder(
            @PathVariable("orderId") Long orderId
    ) {
        return service.deleteOrder(orderId);
    }

    @DeleteMapping("/{orderId}/deleteDetail/{detailId}")
    public OrderView deleteOrderDetail(
            @PathVariable("orderId") Long orderId,
            @PathVariable("detailId") Long detailId
    ) {
        OrderDetail detail = detailService.getDetailById(detailId);
        Double price = detail.getBook().getPrice() * detail.getBooksCount();
        detailService.deleteOrderDetail(detailId);
        Order order = service.getOrderById(orderId);
        order.setSum(order.getSum() - price);
        return mapper.mapToView(service.update(order));
    }

}
