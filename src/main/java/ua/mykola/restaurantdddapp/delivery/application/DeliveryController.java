package ua.mykola.restaurantdddapp.delivery.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mykola.restaurantdddapp.delivery.domain.model.Courier;
import ua.mykola.restaurantdddapp.delivery.domain.model.Delivery;
import ua.mykola.restaurantdddapp.delivery.domain.service.DeliveryService;


import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping()
    public ResponseEntity<List<Delivery>> getAll() {
        return ResponseEntity.ok(deliveryService.getAll());
    }

    @GetMapping("/new")
    public ResponseEntity<List<Delivery>> getNewDeliveries() {
        return ResponseEntity.ok(deliveryService.getNewDeliveries());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Delivery> getByOrderId(@PathVariable long orderId) {
        return ResponseEntity.ok(deliveryService.getDeliveryByOrderId(orderId));
    }

    @GetMapping("/{orderId}/start")
    public ResponseEntity<String> startDelivery(@PathVariable long orderId,
                                                @RequestParam String name,
                                                @RequestParam String phone) {
        deliveryService.startDelivery(orderId, new Courier(name, phone));
        return ResponseEntity.ok("Delivery started");
    }

    @GetMapping("/{orderId}/complete")
    public ResponseEntity<String> startDelivery(@PathVariable long orderId) {
        deliveryService.markOrderAsCompleted(orderId);
        return ResponseEntity.ok("Delivery completed");
    }
}
