package com.example.demo.model;

public class UserAccountForm {

    private Account account;
    private User user;

    public UserAccountForm() {
        this.account = new Account();
        this.user = new User();
    }

    // Getters and Setters
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
