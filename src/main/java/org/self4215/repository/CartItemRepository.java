package org.self4215.repository;

import org.self4215.entity.CartItem;
import org.self4215.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 根据用户查询购物车项
    List<CartItem> findByUser(User user);
    // 根据用户和商品查询购物车项（判断是否已加入购物车）
    CartItem findByUserAndProduct(User user, org.self4215.entity.Product product);
}