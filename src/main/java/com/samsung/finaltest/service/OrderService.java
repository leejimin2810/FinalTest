package com.samsung.finaltest.service;

import com.samsung.finaltest.models.*;
import com.samsung.finaltest.repository.OrderDetailRepository;
import com.samsung.finaltest.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartService = cartService;
    }

    public void placeOrder(User user) {
        List<CartItem> cart = cartService.getCart();
        if (cart.isEmpty()) return;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        orderRepository.save(order);

        for (CartItem item : cart) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(new Product(item.getProductId(), item.getName(), item.getPrice(), item.getImage())); // ✅ Thêm Product vào
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getTotalPrice());
            orderDetailRepository.save(orderDetail); // Lưu từng chi tiết đơn hàng
        }

        cartService.clearCart();
    }
}
