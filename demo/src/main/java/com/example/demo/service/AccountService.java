package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(String id, Account account) {
        if (accountRepository.existsById(id)) {
            account.setAccountId(id);
            return accountRepository.save(account);
        }
        return null;
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
    public boolean existsByAccountId(String accountId) {
        return accountRepository.existsById(accountId);
    }

    public boolean existsByAccountName(String name) {
        return accountRepository.existsByName(name);
    }
    

}
