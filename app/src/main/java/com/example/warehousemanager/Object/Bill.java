package com.example.warehousemanager.Object;

public class Bill {
    private String address;
    private String id;
    private String id_user;
    private String name;
    private int status;
    private int totalMoney;
    private String phone;

    public Bill() {
    }

    public Bill(String address, String id, String id_user, String name, int status, int totalMoney, String phone) {
        this.address = address;
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.status = status;
        this.totalMoney = totalMoney;
        this.phone = phone;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
