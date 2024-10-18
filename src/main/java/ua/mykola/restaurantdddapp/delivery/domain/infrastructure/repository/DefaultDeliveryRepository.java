package ua.mykola.restaurantdddapp.delivery.domain.infrastructure.repository;

import org.springframework.stereotype.Repository;
import ua.mykola.restaurantdddapp.delivery.domain.model.Delivery;
import ua.mykola.restaurantdddapp.delivery.domain.model.DeliveryStatus;
import ua.mykola.restaurantdddapp.delivery.domain.repository.DeliveryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DefaultDeliveryRepository implements DeliveryRepository {
    private Map<Long, Delivery> deliveries = new HashMap<>();

    @Override
    public Optional<Delivery> findByOrderId(Long orderId) {
        return Optional.ofNullable(deliveries.get(orderId));
    }

    @Override
    public List<Delivery> getNewDeliveries() {
        return deliveries.values().stream()
                .filter(delivery -> DeliveryStatus.NEW_DELIVERY.equals(delivery.getStatus()))
                .toList();
    }

    @Override
    public List<Delivery> getAll() {
        return deliveries.values().stream().toList();
    }

    @Override
    public void addDelivery(Delivery delivery) {
        deliveries.put(delivery.getOrderId(), delivery);
    }

    @Override
    public void update(Delivery delivery) {
        deliveries.put(delivery.getOrderId(), delivery);
    }


}
