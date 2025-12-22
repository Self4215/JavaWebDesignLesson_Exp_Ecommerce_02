package org.self4215.controller;

import org.self4215.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 商品列表页
    @GetMapping("/products")
    public String productList(Model model, Authentication authentication) {
        // 获取当前登录用户名（用于后续购物车操作）
        String username = authentication.getName();
        // 查询所有商品并传递到前端页面
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("username", username);
        return "products"; // 对应 templates/products.html
    }
}