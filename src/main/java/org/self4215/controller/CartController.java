package org.self4215.controller;

import org.self4215.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 加入购物车
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Authentication authentication) {
        String username = authentication.getName();
        cartService.addToCart(username, productId, quantity);
        return "redirect:/products"; // 跳转回商品列表页
    }

    // 查看购物车
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        String username = authentication.getName();
        // 获取购物车项并传递到前端
        model.addAttribute("cartItems", cartService.getCartItems(username));
        model.addAttribute("username", username);
        return "cart"; // 对应 templates/cart.html
    }

    // 删除购物车项
    @GetMapping("/remove/{id}")
    public String removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return "redirect:/cart"; // 跳转回购物车页
    }

    // 结算购物车
    @GetMapping("/checkout")
    public String checkout(Authentication authentication) {
        String username = authentication.getName();
        cartService.clearCart(username); // 清空购物车（简化版：无订单逻辑）
        return "redirect:/products?checkout=success"; // 结算成功跳转商品列表
    }
}