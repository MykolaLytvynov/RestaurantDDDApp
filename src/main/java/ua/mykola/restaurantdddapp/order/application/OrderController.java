package ua.mykola.restaurantdddapp.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mykola.restaurantdddapp.order.domain.model.Order;
import ua.mykola.restaurantdddapp.order.domain.model.MenuItem;
import ua.mykola.restaurantdddapp.order.domain.model.OrderStatus;
import ua.mykola.restaurantdddapp.order.domain.service.OrderService;

import java.util.Arrays;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    @PostMapping("/{orderId}/items")
    public void addItemToOrder(@PathVariable Long orderId, @RequestBody MenuItem item) {
        orderService.addItemToOrder(orderId, item);
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable Long orderId, @RequestParam String status) {
        OrderStatus newStatus;
        switch (status) {
            case "IN_PROCESS" -> newStatus = OrderStatus.IN_DELIVERING;
            case "DELIVERED" -> newStatus = OrderStatus.COMPLETED;
            default -> {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid status");
            }
        }

        orderService.changeStatus(orderId, newStatus);
        return ResponseEntity.ok("Order status changed");
    }


}
