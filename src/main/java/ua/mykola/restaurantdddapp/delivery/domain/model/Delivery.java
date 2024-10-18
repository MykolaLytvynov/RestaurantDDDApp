package ua.mykola.restaurantdddapp.delivery.domain.model;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Delivery {
    private Long orderId;
    private String description;
    private DeliveryStatus status;
    private Courier courier;
}
