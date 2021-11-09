package com.example.warehousemanager.Product;

public class Product {
    private String hinhAnh;
    private String tenHinhAnh;
    private String id;
    private String tenSanPham;
    private int giaTien;
    private String category;
    private int stock;
    private int sold;
    private String manufacturer;

    public Product() {

    }

    public Product(String hinhAnh, String tenHinhAnh, String id, String tenSanPham, int giaTien, String category, int stock, int sold, String manufacturer) {
        this.hinhAnh = hinhAnh;
        this.tenHinhAnh = tenHinhAnh;
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.category = category;
        this.stock = stock;
        this.sold = sold;
        this.manufacturer = manufacturer;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenHinhAnh() {
        return tenHinhAnh;
    }

    public void setTenHinhAnh(String tenHinhAnh) {
        this.tenHinhAnh = tenHinhAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

