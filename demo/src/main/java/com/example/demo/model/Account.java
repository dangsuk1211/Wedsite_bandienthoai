package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "account")
public class Account {

	@Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_name")
    private String name;

    @Column(name = "password")
    private String pass;

    @Column(name = "account_type")
    private String type;

    public Account() {}

    public Account(String accountId, String name, String pass, String type) {
        this.accountId = accountId;
        this.name = name;
        this.pass = pass;
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
