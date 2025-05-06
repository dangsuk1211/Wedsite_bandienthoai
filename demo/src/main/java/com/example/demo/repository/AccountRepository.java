package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByName(String name);
    boolean existsById(String accountId);
    boolean existsByName(String name);
    
}