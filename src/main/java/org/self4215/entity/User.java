package org.self4215.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users") // 数据库表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增ID
    private Long id;

    @Column(unique = true, nullable = false) // 用户名唯一、非空
    private String username;

    @Column(nullable = false)
    private String password; // 密码（Spring Security 会加密）

    private String fullName; // 昵称

    // 关联购物车（一个用户对应多个购物车项）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 【新增】防止 toString 死循环
    private Set<CartItem> cartItems;
}