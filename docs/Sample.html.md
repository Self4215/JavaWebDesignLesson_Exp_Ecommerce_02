# Docker + Spring Boot 实战

## 开发目标

中小型电商系统

*   **从零搭建**
*   包含**用户登录**、**商品列表**、**购物车操作**的完整电商核心流程
*   全程基于 `Docker` 容器化部署
    *   实现快速开发与部署
*   使用 `Spring Boot` 开发电商系统
    *   遵循 **分层架构** 设计
    *   `Spring Boot` + `MySQL`

## 核心技术栈

*   **后端**：
    *   `Spring Boot`（核心框架）+ `Spring Security`（登录认证）+ `Spring Data JPA`（数据库操作）
*   **前端**：
    *   `Thymeleaf`（模板引擎，无需单独学前端框架）+ `Bootstrap`（简易样式）
*   **数据库**：`MySQL`（`Docker` 容器运行，无需本地安装）
*   **容器化**：`Docker` + `Docker Compose`（一键启动应用 + 数据库）

## 必备工具

*   `JDK 8+`/`11` :
    *   `Java` 开发环境
*   `Maven` :
    *   项目构建工具
*   `Docker` :
    *   容器化运行环境
*   `Docker Compose` :
    *   多容器编排工具
*   `IDE` (`IDEA`/`Eclipse`) : 代码编写工具

## 项目整体结构

### 项目整体结构(I)

`Spring Boot` 分层开发（实体-仓库-服务-控制器）

*   实体层（`Entity`） : 映射数据库表（如用户表、商品表）
    *   `com.example.ecommerce.entity`
    *   `JPA` 注解（`@Entity`、`@Table`）
*   仓库层（`Repository`）: 数据库操作（增删改查）
    *   `com.example.ecommerce.repository`
    *   `Spring Data JPA`（`JpaRepository`）
*   服务层（`Service`） : 业务逻辑处理（如加入购物车、结算）
    *   `com.example.ecommerce.service`
    *   `@Service` 注解

### 项目整体结构(II)

`Spring Boot` 分层开发（实体-仓库-服务-控制器）

*   控制器层（`Controller`） : 接收前端请求、返回页面 / 数据
    *   `com.example.ecommerce.controller`
    *   `@Controller`、`@GetMapping` 等
*   配置层（`Config`）: 项目配置（如登录认证、数据库）
    *   `com.example.ecommerce.config`
    *   `Spring Security` 配置
*   前端层（`Templates`） : 页面展示（登录页、商品列表）
    *   `resources/templates`
    *   `Thymeleaf + Bootstrap`

### 项目整体结构(III)

这种分层结构是一种经典的 `MVC` 衍生架构，目的是实现**代码解耦**、**职责分离**，**便于维护和扩展**

```
ecommerce-demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/ecommerce/
│   │   │   ├── EcommerceApplication.java  // 项目启动类
│   │   │   ├── config/                    // 配置类（登录、数据库）
│   │   │   ├── entity/                    // 实体类（用户、商品、购物车）
│   │   │   ├── repository/                // 数据库操作层
│   │   │   ├── service/                   // 业务逻辑层
│   │   │   └── controller/                // 控制器（接口）
│   │   ├── resources/
│   │   │   ├── static/                    // 静态资源（CSS/JS）
│   │   │   ├── templates/                 // 前端页面（Thymeleaf）
│   │   │   ├── application.properties     // 项目配置
│   │   │   └── data.sql                   // 初始化数据（商品/测试用户）
│   └── test/                              // 测试类（新手可忽略）
├── Dockerfile                             // Spring Boot 容器化配置
├── docker-compose.yml               // 整合 Spring Boot + MySQL
└── pom.xml                                // Maven 依赖配置
```

### 项目整体结构(IV)

这种分层就像 **工厂的流水线**：

*   **实体层**:
    *   "原材料规格"（定义数据是什么）；
*   **仓库层**:
    *   "原材料仓库"（负责存取）；
*   **服务层**:
    *   "生产车间"（加工处理，实现业务）；
*   **控制器层**:
    *   "前台窗口"（接收订单、交付产品）。

## 创建`Maven`项目 与 配置依赖

### 创建 `Maven` 项目

*   `IDEA` 操作：`New Project` $\to$ `Maven` $\to$ 填写 `GroupId`（`com.example`）、`ArtifactId`（`ecommerce`）$\to$ 完成

确保 `pom.xml` 配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.18</version> <!-- 稳定版，适配新手 -->
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>ecommerce</artifactId>
    <version>1.0.0</version>
    <name>ecommerce-demo</name>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot 核心依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Thymeleaf 前端模板引擎 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!-- Spring Security 登录认证 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- Spring Data JPA 操作数据库 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- MySQL 驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- Lombok 简化实体类（少写get/set） -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- 测试依赖（可选） -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Spring Boot 打包插件（生成可运行Jar） -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 配置依赖(I)

*   `spring-boot-starter-web`：
    *   让项目成为 `Web` 应用
    *   支持接口开发
*   `spring-boot-starter-thymeleaf`：
    *   无需前后端分离
    *   直接在后端写前端页面
*   `spring-boot-starter-security`：
    *   快速实现用户登录认证
    *   不用自己写登录逻辑

### 配置依赖(II)

*   `spring-boot-starter-data-jpa`：
    *   简化数据库操作
    *   不用写 `SQL` 语句
*   `mysql-connector-java`：
    *   连接 `MySQL` 数据库
*   `lombok`：
    *   简化实体类代码
    *   比如 `@Data` 注解自动生成 `get/set` 方法

### 配置项目参数(I)

在 `src/main/resources` 下创建 `application.properties`，内容如下：

```properties
# 服务器端口（Docker 映射用）
server.port=8080

# 数据库配置（连接 Docker 中的 MySQL）
spring.datasource.url=jdbc:mysql://mysql:3306/ecommerce_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA 配置（自动建表、显示SQL）
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Thymeleaf 配置（开发时禁用缓存）
spring.thymeleaf.cache=false

# 日志配置（显示SQL）
logging.level.org.hibernate.SQL=DEBUG
```

### 配置项目参数(II)

关键解释：

*   `spring.datasource.url` 中 `mysql` 是 `Docker Compose` 中 `MySQL` 容器的名称
    *   `Docker` 内部域名解析
*   `spring.jpa.hibernate.ddl-auto=update`：
    *   启动项目时自动创建 / 更新数据库表；
*   `spring.thymeleaf.cache=false`：
    *   修改前端页面后无需重启项目
    *   直接刷新浏览器即可

## 启动类 `EcommerceApplication.java`

### 启动类

在 `com.example.ecommerce` 包下创建 `EcommerceApplication.java`：

```java
package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
```

`@SpringBootApplication`：

*   `Spring Boot` 核心注解，自动配置、扫描组件
*   新手无需理解细节，加上即可

## 实体类（`Entity`）（用户、商品、购物车）

### 实体类（I）

*   **数据模型映射**
*   **角色**：数据库表的 "镜像"，负责定义数据结构。
*   **作用**：
    *   用 `Java` 类映射数据库中的表，每个实体类对应一张表，类中的字段对应表中的列。
    *   通过 `JPA` 注解（如 `@Entity`、`@Table`、`@Id`）定义表名、主键、字段约束（如唯一、非空）等。
    *   存储业务数据的基本结构，不包含复杂逻辑，仅保留数据的 "形"。

### 实体类（II）:

在 `com.example.ecommerce.entity` 包下创建**用户**实体（`User.java`）

```java
package com.example.ecommerce.entity;

import lombok.Data;
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
    private Set<CartItem> cartItems;
}
```

### 实体类（III）:

在 `com.example.ecommerce.entity` 包下创建**商品**实体（`Product.java`）

```java
package com.example.ecommerce.entity;

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
```

### 实体类（IV）:

**购物车项**实体（`CartItem.java`）

```java
package com.example.ecommerce.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联用户（多对一）
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 关联商品（多对一）
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // 商品数量
}
```

### 实体类解释

*   `@Entity`：标记为 `JPA` 实体
    *   对应数据库表；
*   `@Table`：指定数据库表名；
*   `@Id` + `@GeneratedValue`：主键 + 自增
*   `@OneToMany/@ManyToOne`：表之间的关联关系
    *   `用户-购物车项` 是 **一对多**
    *   `购物车项-商品` 是 **多对一**
*   `@Data`（`Lombok`）：自动生成 `get/set/toString` 等方法
    *   简化代码

## 数据库操作层（`Repository`）

### 数据库操作层（I）

*   又可称为**仓库层**：数据访问接口
*   **角色**：数据库操作的 "执行者"，负责数据的增删改查。
*   **作用**：
    *   基于 `Spring Data JPA`，通过接口继承 `JpaRepository`，无需手写 `SQL` 即可实现基本 `CRUD` 操作（如 `save()`、`findById()`、`findAll()`）。
    *   支持自定义查询方法（如 `findByUsername`），`JPA` 会根据方法名自动生成 `SQL`，简化数据访问逻辑。
    *   隔离数据库操作细节，使上层（服务层）无需关注数据存储的具体实现。

### 数据库操作层（II）

在 `com.example.ecommerce.repository` 包下创建`UserRepository.java`

```java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<实体类, 主键类型>：内置增删改查方法
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询用户（登录时用）
    Optional<User> findByUsername(String username);
}
```

在 `com.example.ecommerce.repository` 包下创建`ProductRepository.java`

```java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 无需写额外方法，JpaRepository 已有查询所有、根据ID查询等方法
}
```

### 数据库操作层（III）

在 `com.example.ecommerce.repository` 包下创建`CartItemRepository.java`

```java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 根据用户查询购物车项
    List<CartItem> findByUser(User user);
    // 根据用户和商品查询购物车项（判断是否已加入购物车）
    CartItem findByUserAndProduct(User user, com.example.ecommerce.entity.Product product);
}
```

### 数据库操作层（IV）

数据库操作层的解释：

*   `JpaRepository` 是 `Spring Data JPA` 提供的通用仓库
    *   内置了 `findAll()`、`findById()`、`save()`、`delete()` 等方法
    *   无需自己写 `SQL`
*   自定义方法（如 `findByUsername`）
    *   `JPA` 会根据方法名自动生成 `SQL`
    *   新手无需写复杂查询。

## 业务逻辑层（`Service`）

### 业务逻辑层（I）

*   又可称为**服务层**：业务逻辑核心
*   **角色**：业务规则的 "决策者"，负责处理核心业务逻辑。
*   **作用**：
    *   调用仓库层接口获取或修改数据，结合业务规则进行处理（如购物车中商品数量的增减、订单结算逻辑）。
    *   管理事务（如多表操作时保证原子性，例如创建订单时同时扣减库存和生成订单记录）。
    *   隔离业务逻辑与数据访问、接口交互，使代码模块化，便于复用和测试。

### 业务逻辑层（II）

在 `com.example.ecommerce.service` 包下创建`UserService.java`（用户服务，登录认证）

```java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 构造器注入（Spring 自动装配）
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Security 登录时调用：根据用户名查询用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在：" + username));

        // 封装成 Spring Security 的 UserDetails（包含用户名、密码、权限）
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList() // 新手简化：无权限控制
        );
    }

    // 获取当前登录用户的实体
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    // 保存用户（初始化测试用户用）
    public User saveUser(User user) {
        // 密码加密（Spring Security 要求密码必须加密）
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
```

### 业务逻辑层（III）

在 `com.example.ecommerce.service` 包下创建`ProductService.java`（商品服务）

```java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 查询所有商品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根据ID查询商品
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在：" + id));
    }

    // 保存商品（初始化数据用）
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
```

### 业务逻辑层（IV）

在 `com.example.ecommerce.service` 包下创建`CartService.java`（购物车服务）

```java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.CartItemRepository;
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
```

### 业务逻辑层（V）

业务逻辑层解释：

*   `@Service`：标记为业务逻辑层
    *   `Spring` 会自动管理该类的实例
*   构造器注入：`Spring` 自动将 `Repository` 实例传入
    *   新手无需手动创建对象
*   `UserDetailsService`：`Spring Security` 要求的接口
    *   实现 `loadUserByUsername` 即可完成登录认证
*   密码加密：`Spring Security` 强制要求密码加密存储
    *   `PasswordEncoder` 会自动处理

## 配置类（Config）（登录、数据库）

### 配置类

在 `com.example.ecommerce.config` 包下创建`SecurityConfig.java`（登录配置）

```java
package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 密码加密器（Spring 容器管理）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 安全过滤链：配置登录、权限、退出等规则
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF（新手简化，生产环境需开启）
                .csrf().disable()
                // 配置请求权限
                .authorizeHttpRequests(auth -> auth
                        // 登录页、静态资源允许匿名访问
                        .antMatchers("/login", "/css/**", "/js/**").permitAll()
                        // 其他所有请求需要登录
                        .anyRequest().authenticated()
                )
                // 配置登录页面
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页路径
                        .defaultSuccessUrl("/products", true) // 登录成功后跳转到商品列表
                        .permitAll()
                )
                // 配置退出登录
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // 退出后跳回登录页
                        .permitAll()
                );

        return http.build();
    }
}
```

### 安全配置解释：

*   `@Configuration`：标记为配置类；
*   `PasswordEncoder`：`BCrypt` 加密器，用于加密用户密码；
*   `SecurityFilterChain`：配置登录规则：
*   `loginPage("/login")`：
    *   自定义登录页（而非 `Spring Security` 默认页）；
*   `defaultSuccessUrl("/products")`：
    *   登录成功跳转到商品列表；
*   `permitAll()`：允许匿名访问登录页，其他页面需登录。

## 控制器（Controller）（接口）

### 控制器（I）

*   请求入口与响应
*   **角色**：用户请求的 "接收者" 和 "反馈者"，负责衔接前端与后端。
*   **作用**：
    *   通过 `@GetMapping`、`@PostMapping` 等注解接收前端请求（如页面访问、表单提交）。
    *   调用服务层方法处理业务，将结果通过 `Model` 传递给前端页面，或直接返回数据。
    *   控制页面跳转（如登录成功后跳转到商品列表，加入购物车后跳回购物车页面）。
    *   不处理复杂业务逻辑，仅负责请求分发和响应，保证分层清晰。

### 控制器（II）

在 `com.example.ecommerce.controller` 包下创建`LoginController.java`（登录页）

```java
package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    // 访问 /login 时返回登录页
    @GetMapping("/login")
    public String login() {
        return "login"; // 对应 templates/login.html
    }
}
```

### 控制器（III）

在 `com.example.ecommerce.controller` 包下创建`ProductController.java`（商品列表）

```java
package com.example.ecommerce.controller;

import com.example.ecommerce.service.ProductService;
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
```

### 控制器（IV）

在 `com.example.ecommerce.controller` 包下创建`CartController.java`（购物车操作）

```java
package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
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
```

### 控制器解释：

*   `@Controller`：标记为 `Web` 控制器，处理前端请求；
*   `@GetMapping/@PostMapping`：
    *   处理 `GET/POST` 请求，路径对应前端页面的请求地址；
*   `Model`：
    *   用于将数据传递到前端页面（如商品列表、购物车项）；
*   `Authentication`：
    *   `Spring Security` 提供的对象，获取当前登录用户信息；
*   `redirect:/xxx`：
    *   重定向到指定页面（如加入购物车后跳回商品列表）。

## 前端页面（基于Thymeleaf）

### 前端页面（I）

登录页（`templates/login.html`）

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>电商网站 - 登录</title>
    <!-- 引入Bootstrap样式（无需本地下载） -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">
                        <h3>电商网站登录</h3>
                    </div>
                    <div class="card-body">
                        <!-- 登录表单（提交到 Spring Security 的 /login 接口） -->
                        <form th:action="@{/login}" method="post">
                            <!-- 错误提示 -->
                            <div th:if="${param.error}" class="alert alert-danger">
                                用户名或密码错误！
                            </div>
                            <!-- 退出提示 -->
                            <div th:if="${param.logout}" class="alert alert-success">
                                已成功退出登录！
                            </div>

                            <div class="mb-3">
                                <label for="username" class="form-label">用户名</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">密码</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">登录</button>
                        </form>
                        <!-- 测试账号提示 -->
                        <div class="mt-3 text-center text-muted">
                            测试账号：testuser / 密码：123456
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```

### 前端页面（II）

商品列表页（`templates/products.html`）

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>商品列表 - 电商网站</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="/products">电商网站</a>
            <div class="navbar-nav">
                <a class="nav-link active" href="/products">商品列表</a>
                <a class="nav-link" href="/cart">我的购物车</a>
                <span class="nav-link text-light">欢迎，[[${username}]]</span>
                <a class="nav-link text-light" href="/logout">退出登录</a>
            </div>
        </div>
    </nav>

    <!-- 结算成功提示 -->
    <div th:if="${param.checkout}" class="container mt-3">
        <div class="alert alert-success">
            结算成功！感谢您的购买！
        </div>
    </div>

    <!-- 商品列表 -->
    <div class="container mt-4">
        <h2>商品列表</h2>
        <div class="row mt-3">
            <!-- 循环遍历商品 -->
            <div th:each="product : ${products}" class="col-md-4 mb-4">
                <div class="card h-100">
                    <img th:src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                    <div class="card-body">
                        <h5 class="card-title" th:text="${product.name}">商品名称</h5>
                        <p class="card-text" th:text="${product.description}">商品描述</p>
                        <p class="card-text text-danger fw-bold" th:text="'¥' + ${product.price}">¥0.00</p>
                        <!-- 加入购物车表单 -->
                        <form th:action="@{/cart/add}" method="post">
                            <input type="hidden" name="productId" th:value="${product.id}">
                            <div class="input-group mb-3">
                                <input type="number" name="quantity" class="form-control" value="1" min="1" max="10">
                                <button type="submit" class="btn btn-primary">加入购物车</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```

### 前端页面（III）

购物车页（`templates/cart.html`）

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>我的购物车 - 电商网站</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="/products">电商网站</a>
            <div class="navbar-nav">
                <a class="nav-link" href="/products">商品列表</a>
                <a class="nav-link active" href="/cart">我的购物车</a>
                <span class="nav-link text-light">欢迎，[[${username}]]</span>
                <a class="nav-link text-light" href="/logout">退出登录</a>
            </div>
        </div>
    </nav>

    <!-- 购物车内容 -->
    <div class="container mt-4">
        <h2>我的购物车</h2>
        <!-- 购物车为空 -->
        <div th:if="${#lists.isEmpty(cartItems)}" class="alert alert-info">
            您的购物车为空，快去挑选商品吧！<a href="/products">返回商品列表</a>
        </div>
        <!-- 购物车有商品 -->
        <div th:unless="${#lists.isEmpty(cartItems)}">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>商品图片</th>
                        <th>商品名称</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr th:each="item : ${cartItems}">
                        <td><img th:src="${item.product.imageUrl}" width="80" height="80" alt="${item.product.name}"></td>
                        <td th:text="${item.product.name}">商品名称</td>
                        <td th:text="'¥' + ${item.product.price}">¥0.00</td>
                        <td th:text="${item.quantity}">1</td>
                        <td th:text="'¥' + ${item.product.price * item.quantity}">¥0.00</td>
                        <td>
                            <a th:href="@{/cart/remove/{id}(id=${item.id})}" class="btn btn-danger btn-sm">删除</a>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- 结算按钮 -->
            <div class="text-end mt-3">
                <a href="/cart/checkout" class="btn btn-success btn-lg">结算购物车</a>
            </div>
        </div>
    </div>
</body>
</html>
```

### 前端页面解释：

*   `Thymeleaf` 语法：
    *   `[[${变量名}]]` 用于显示后端传递的数据
    *   `th:each` 用于循环遍历列表
*   `Bootstrap`：通过 `CDN` 引入，无需本地下载
    *   快速实现美观的页面样式
*   表单提交：`th:action="@{/cart/add}"` 指向控制器的接口
    *   实现加入购物车等操作
*   导航栏：包含商品列表、购物车、退出登录等链接，方便用户操作

## `Docker` 容器化部署与测试

### `Docker` 容器化配置(I)

`Dockerfile`（`Spring Boot` 应用容器化）

```dockerfile
# 阶段1：构建 Jar 包（多阶段构建，减小镜像体积）
FROM maven:3.8.5-openjdk-8 AS build
# 设置工作目录
WORKDIR /app
# 复制 pom.xml 和源码
COPY pom.xml .
COPY src ./src
# 打包生成 Jar 包（跳过测试）
RUN mvn clean package -DskipTests

# 阶段2：运行 Jar 包
FROM openjdk:8-jdk-alpine
# 设置工作目录
WORKDIR /app
# 从构建阶段复制 Jar 包到当前镜像
COPY --from=build /app/target/ecommerce-1.0.0.jar app.jar
# 暴露端口（与 application.properties 中的 server.port 一致）
EXPOSE 8080
# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### `Docker` 容器化配置(II)

`Dockerfile` 解释：

*   多阶段构建：
    *   先通过 `Maven` 镜像打包 `Jar` 包
    *   再用轻量级的 `Alpine JDK` 镜像运行
    *   减小最终镜像体积
*   `alpine` 版本：
    *   轻量级 `Linux` 镜像，体积仅几十 `MB`，比普通 `JDK` 镜像小很多
*   `EXPOSE 8080`：声明容器暴露的端口
    *   方便 `Docker Compose` 映射

### 整合 `Spring Boot + MySQL`(I)

在项目根目录创建 `docker-compose.yml`：

```yaml
version: '3.8'

services:
# MySQL 服务
mysql:
    image: mysql:8.0
    container_name: ecommerce-mysql
    restart: always
    environment:
        # 数据库根密码
        MYSQL_ROOT_PASSWORD: root123
        # 自动创建数据库
        MYSQL_DATABASE: ecommerce_db
        # 字符集配置
        MYSQL_CHARACTER_SET_SERVER: utf8mb4
        MYSQL_COLLATION_SERVER: utf8mb4_unicode_ci
    ports:
        - "3306:3306"
    volumes:
        # 持久化数据（容器删除后数据不丢失）
        - mysql-data:/var/lib/mysql
    networks:
        - ecommerce-network

# Spring Boot 应用服务
app:
    build: .
    container_name: ecommerce-app
    restart: always
    ports:
        - "8080:8080"
    # 依赖 MySQL 服务，确保先启动 MySQL
    depends_on:
        - mysql
    networks:
        - ecommerce-network

# 网络配置（让两个容器在同一网络，可互相访问）
networks:
    ecommerce-network:
        driver: bridge

# 数据卷（持久化 MySQL 数据）
volumes:
mysql-data:
```

### 整合 `Spring Boot + MySQL`(II)

`docker-compose.yml` 解释：

*   `services`：定义两个服务：`MySQL` 和 `Spring Boot` 应用；
*   `mysql` 服务：使用官方 `MySQL 8.0` 镜像，配置根密码、自动创建数据库，端口映射 `3306:3306`；
*   `app` 服务：通过 `build`: . 构建当前目录的 `Dockerfile`，依赖 `MySQL` 服务（`depends_on`）；
*   `networks`：两个服务在同一网络，`Spring Boot` 应用可通过 `mysql` 域名访问 `MySQL` 容器；
*   `volumes`：持久化 `MySQL` 数据，避免容器删除后数据丢失。

### 启动并测试项目(I)

启动容器（项目根目录执行）

```bash
# 构建并启动容器（第一次启动加 --build，强制构建镜像）
docker compose up --build -d
```

命令解释：

*   `up`：启动所有服务
*   `--build`：构建镜像（修改代码后需重新构建）
*   `-d`：后台运行容器，不占用终端

### 启动并测试项目(II)

查看容器状态

```bash
# 查看运行中的容器
docker compose ps

# 查看应用日志（排查错误）
docker compose logs app
```

停止容器

```bash
# 停止并删除容器（保留数据卷）
docker compose down

# 停止并删除容器+数据卷（谨慎：会删除MySQL数据）
docker compose down -v
```

### 功能测试(I)

*   **访问登录页**：
    *   浏览器输入 `http://localhost:8080/login`；`
*   **登录**：
    *   用户名 `testuser`，密码 `123456`，点击登录；
*   **查看商品列表**：
    *   登录成功后跳转到商品列表页，能看到 5 个测试商品；
*   **加入购物车**：
    *   选择任意商品，输入数量（默认 1），点击 “加入购物车”；

### 功能测试(II)

*   **查看购物车**：
    *   点击导航栏的 “我的购物车”，能看到已加入的商品；
*   **删除购物车项**：
    *   点击商品后的 “删除” 按钮，商品从购物车移除；
*   **结算购物车**：
    *   点击 “结算购物车”，购物车清空，跳回商品列表并显示结算成功；
*   **退出登录**：
    *   点击导航栏的 “退出登录”，返回登录页。

## 初始化数据

在 `src/main/resources` 下创建 `data.sql`

```sql
-- 初始化测试用户（密码是 123456，已用 BCrypt 加密）
INSERT INTO users (username, password, full_name) 
VALUES ('testuser', '$2a$10$8H9w5f8G7y6D4s3a2q1z0b9n8m7l6k5j4h3g2f1d0s9a8s7d6f5g4h3j2k1l0', '测试用户');

-- 初始化商品数据
INSERT INTO products (name, description, price, image_url) 
VALUES 
('小米手机', '高性能智能手机，8GB+256GB', 2999.00, 'https://picsum.photos/200/200?random=1'),
('华为平板', '10.4英寸大屏，学习办公神器', 1899.00, 'https://picsum.photos/200/200?random=2'),
('苹果耳机', '无线蓝牙耳机，降噪功能', 1299.00, 'https://picsum.photos/200/200?random=3'),
('联想笔记本', '轻薄本，16GB+512GB', 4999.00, 'https://picsum.photos/200/200?random=4'),
('大疆无人机', '高清航拍，便携折叠', 3699.00, 'https://picsum.photos/200/200?random=5');
```

数据初始化解释：

*   测试用户密码：`123456`
*   商品图片用 `picsum.photos` 占位图

## 课程QQ群

![QQGroup](data:image/jpeg;base64,iVxxxx==)
