package com.example.springboot_reactjs.repositories;

import com.example.springboot_reactjs.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,Long> {
    InvalidatedToken findByToken(String token);
}
