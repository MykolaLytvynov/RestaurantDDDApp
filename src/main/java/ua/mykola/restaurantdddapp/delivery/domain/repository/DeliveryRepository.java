package ua.mykola.restaurantdddapp.delivery.domain.repository;

import ua.mykola.restaurantdddapp.delivery.domain.model.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {
    Optional<Delivery> findByOrderId(Long orderId);
    List<Delivery> getNewDeliveries();
    void addDelivery(Delivery delivery);
    void update(Delivery delivery);
    List<Delivery> getAll();
}
