package ua.mykola.restaurantdddapp.order.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mykola.restaurantdddapp.order.domain.model.Order;
import ua.mykola.restaurantdddapp.order.domain.model.MenuItem;
import ua.mykola.restaurantdddapp.order.domain.model.OrderStatus;
import ua.mykola.restaurantdddapp.order.domain.repository.OrderRepository;
import ua.mykola.restaurantdddapp.order.infrastructure.messaging.OrderProducer;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order createOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        order.setStatus(OrderStatus.IN_PROCESS);
        order.setTotalPrice(calculateTotalPrice(order.getItems()));
        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrder(savedOrder);
        return savedOrder;
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order by id " + id + " was not found"));
    }

    public void addItemToOrder(Long orderId, MenuItem item) {
        Order order = findOrderById(orderId);
        if (order.getStatus().equals(OrderStatus.IN_PROCESS)) {
            order.addItem(item);
            orderRepository.save(order);
        } else throw new IllegalArgumentException("Order was passed to the delivery team");
    }

    private BigDecimal calculateTotalPrice(List<MenuItem> items) {
        return items.stream()
                .map(MenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void changeStatus(Long orderId, OrderStatus status) {
        Order order = findOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
