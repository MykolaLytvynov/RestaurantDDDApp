package ua.mykola.restaurantdddapp.delivery.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.mykola.restaurantdddapp.delivery.domain.model.Delivery;
import ua.mykola.restaurantdddapp.delivery.domain.service.DeliveryService;

@Service
@RequiredArgsConstructor
public class DeliveryConsumer {
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void listenForOrder(String message) {
        String[] orderData = message.split("&&");
        Delivery newDelivery = Delivery.builder()
                .orderId(Long.valueOf(orderData[0].trim()))
                .description(orderData[1].trim())
                .build();
        deliveryService.addDelivery(newDelivery);
    }

}
