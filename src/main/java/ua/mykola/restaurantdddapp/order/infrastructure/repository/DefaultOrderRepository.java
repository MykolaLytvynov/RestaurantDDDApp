package ua.mykola.restaurantdddapp.order.infrastructure.repository;

import org.springframework.stereotype.Repository;
import ua.mykola.restaurantdddapp.order.domain.model.Order;
import ua.mykola.restaurantdddapp.order.domain.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DefaultOrderRepository implements OrderRepository {
    private final Map<Long, Order> orders = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
        }
        orders.put(order.getId(), order);
        return order;
    }
}
