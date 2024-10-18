package ua.mykola.restaurantdddapp.order.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Order {
    private Long id;
    private List<MenuItem> items;
    private Person client;
    private Address deliveryAddress;
    private BigDecimal totalPrice;
    private OrderStatus status;

    public void addItem(MenuItem item) {
        items.add(item);
        totalPrice = totalPrice.add(item.getPrice());
    }
}
