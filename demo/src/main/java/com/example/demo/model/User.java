package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "id_department")
    private String id_department;
    @Column(name = "old")
    private int old;
    @Column(name = "account_id")
    private String accountuserid;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getDepartment() {
        return id_department;
    }

    public void setDepartment(String department) {
        this.id_department = department;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }
    public String getAccountuserid() {
        return accountuserid;
    }

    public void setAccountuserid(String accountuserid) {
        this.accountuserid = accountuserid;
    }
}
