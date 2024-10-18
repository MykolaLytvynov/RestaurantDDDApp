package ua.mykola.restaurantdddapp.delivery.infrastructure.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.mykola.restaurantdddapp.delivery.domain.model.DeliveryStatus;

@Service
public class OrderStatusClient {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${main-domain.url}")
    private String mainUrl;

    public void startDelivery(Long orderId) {
        doRequest(orderId, DeliveryStatus.IN_PROCESS.name());
    }

    public void markOrderAsCompleted(Long orderId) {
        doRequest(orderId, DeliveryStatus.DELIVERED.name());
    }

    private void doRequest(Long orderId, String status) {
        String url = String.format("%s/orders/%d/status?status=%s",
                mainUrl,
                orderId,
                status);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(String.format("Request to change status for orderId:%d was failed", orderId));
        }
    }
}
