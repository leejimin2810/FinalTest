package com.samsung.finaltest.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private Long productId;
    private String name;
    private double price;
    private String image;
    private int quantity;

    public double getTotalPrice() {
        return price * quantity;
    }
}
