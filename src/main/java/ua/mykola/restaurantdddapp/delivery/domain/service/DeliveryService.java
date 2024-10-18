package ua.mykola.restaurantdddapp.delivery.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mykola.restaurantdddapp.delivery.domain.model.Courier;
import ua.mykola.restaurantdddapp.delivery.domain.model.Delivery;
import ua.mykola.restaurantdddapp.delivery.domain.model.DeliveryStatus;
import ua.mykola.restaurantdddapp.delivery.domain.repository.DeliveryRepository;
import ua.mykola.restaurantdddapp.delivery.infrastructure.communication.OrderStatusClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderStatusClient orderStatusClient;

    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order was not found"));
    }

    public void startDelivery(Long orderId, Courier courier) {
        Delivery delivery = getDeliveryByOrderId(orderId);
        if (!delivery.getStatus().equals(DeliveryStatus.NEW_DELIVERY)) {
            throw new IllegalArgumentException("Operation is not possible: Delivery status is not new delivery");
        }
        orderStatusClient.startDelivery(delivery.getOrderId());

        delivery.setCourier(courier);
        delivery.setStatus(DeliveryStatus.IN_PROCESS);
        deliveryRepository.update(delivery);
    }

    public void markOrderAsCompleted(Long orderId) {
        Delivery delivery = getDeliveryByOrderId(orderId);
        if (!delivery.getStatus().equals(DeliveryStatus.IN_PROCESS)) {
            throw new IllegalArgumentException("Operation is not possible: Delivery status is not in process");
        }

        orderStatusClient.markOrderAsCompleted(delivery.getOrderId());
        delivery.setStatus(DeliveryStatus.DELIVERED);
        deliveryRepository.update(delivery);
    }

    public void addDelivery(Delivery delivery) {
        delivery.setStatus(DeliveryStatus.NEW_DELIVERY);
        deliveryRepository.addDelivery(delivery);
    }

    public List<Delivery> getNewDeliveries() {
        return deliveryRepository.getNewDeliveries();
    }

    public List<Delivery> getAll() {
        return deliveryRepository.getAll();
    }
}
