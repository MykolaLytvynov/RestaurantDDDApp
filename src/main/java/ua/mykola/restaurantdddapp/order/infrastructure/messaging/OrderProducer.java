package ua.mykola.restaurantdddapp.order.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.mykola.restaurantdddapp.order.domain.model.Order;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrder(Order order) {
        String message = """
                %d
                &&
                Client data: %s
                Address: %s
                """.formatted(
                        order.getId(),
                        order.getClient(),
                        order.getDeliveryAddress()
        );
        kafkaTemplate.send("order-topic", order.getId().toString(), message);
    }
}
