package org.self4215.repository;

import org.self4215.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 无需写额外方法，JpaRepository 已有查询所有、根据ID查询等方法
}