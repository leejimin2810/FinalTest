package com.samsung.finaltest.controllers;

import com.samsung.finaltest.models.CartItem;
import com.samsung.finaltest.models.User;
import com.samsung.finaltest.repository.ProductRepository;
import com.samsung.finaltest.service.CartService;
import com.samsung.finaltest.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;


    public CartController(CartService cartService, OrderService orderService){
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping
    public String viewCart(Model model) {
        List<CartItem> cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("hasItems", !cart.isEmpty());
        return "cart";
    }


    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        orderService.placeOrder(user);
        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order_success";
    }


}
