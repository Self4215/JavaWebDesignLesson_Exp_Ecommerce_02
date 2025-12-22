package org.self4215.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 商品名称

    private String description; // 商品描述

    @Column(nullable = false)
    private BigDecimal price; // 商品价格

    private String imageUrl; // 商品图片（用占位图）
}