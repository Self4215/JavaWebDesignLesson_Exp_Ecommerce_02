package org.self4215.repository;

import org.self4215.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<实体类, 主键类型>：内置增删改查方法
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询用户（登录时用）
    Optional<User> findByUsername(String username);
}