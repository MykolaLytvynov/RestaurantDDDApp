package ua.mykola.restaurantdddapp.order.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItem {
    private String name;
    private BigDecimal price;
}
