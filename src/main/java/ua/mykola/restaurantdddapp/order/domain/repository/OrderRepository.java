package ua.mykola.restaurantdddapp.order.domain.repository;

import ua.mykola.restaurantdddapp.order.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long orderId);
    Order save(Order order);
}
