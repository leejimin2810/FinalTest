package com.samsung.finaltest.service;

import com.samsung.finaltest.models.CartItem;
import com.samsung.finaltest.models.Product;
import com.samsung.finaltest.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final HttpSession session;

    public CartService(ProductRepository productRepository, HttpSession session) {
        this.productRepository = productRepository;
        this.session = session;
    }

    public List<CartItem> getCart() {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        return (cart != null) ? cart : new ArrayList<>();
    }

    public void addToCart(Long productId, int quantity) {
        List<CartItem> cart = getCart();
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            CartItem item = cart.stream()
                    .filter(c -> c.getProductId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                cart.add(new CartItem(productId, product.getName(), product.getPrice(), product.getImage(), quantity));
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }

            session.setAttribute("cart", cart);
        }
    }

    public void updateQuantity(Long productId, int quantity) {
        List<CartItem> cart = getCart();
        cart.stream()
                .filter(c -> c.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        session.setAttribute("cart", cart);
    }

    public void removeFromCart(Long productId) {
        List<CartItem> cart = getCart();
        cart.removeIf(item -> item.getProductId().equals(productId));
        session.setAttribute("cart", cart);
    }

    public void clearCart() {
        session.removeAttribute("cart");
    }
}
