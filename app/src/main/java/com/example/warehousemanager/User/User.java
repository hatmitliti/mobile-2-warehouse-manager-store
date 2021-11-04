package com.example.warehousemanager.User;

public class User {
    private String id, name, phone, rank,address, imgUser,nameIMGUser,email;
    private double totalMoney;

    public User() {
    }

    public User(String id, String name,String email, String phone, String rank, String address, String anhUser, String tenAnhUser, double totalMoney) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.rank = rank;
        this.address= address;
        this.imgUser = anhUser;
        this.nameIMGUser = tenAnhUser;
        this.totalMoney = totalMoney;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDiaChi() {
        return address;
    }

    public void setDiaChi(String diaChi) {
        this.address = diaChi;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getNameIMGUser() {
        return nameIMGUser;
    }

    public void setNameIMGUser(String nameIMGUser) {
        this.nameIMGUser = nameIMGUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
