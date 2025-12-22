package org.self4215.service;

import org.self4215.entity.CartItem;
import org.self4215.entity.Product;
import org.self4215.entity.User;
import org.self4215.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartService(CartItemRepository cartItemRepository, ProductService productService, UserService userService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userService = userService;
    }

    // 加入购物车
    public void addToCart(String username, Long productId, Integer quantity) {
        // 获取当前用户和商品
        User user = userService.getCurrentUser(username);
        Product product = productService.getProductById(productId);

        // 检查是否已加入购物车：已存在则更新数量，不存在则新增
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }
        cartItemRepository.save(cartItem);
    }

    // 获取用户购物车
    public List<CartItem> getCartItems(String username) {
        User user = userService.getCurrentUser(username);
        return cartItemRepository.findByUser(user);
    }

    // 删除购物车项
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 清空购物车（结算后调用）
    public void clearCart(String username) {
        User user = userService.getCurrentUser(username);
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
}