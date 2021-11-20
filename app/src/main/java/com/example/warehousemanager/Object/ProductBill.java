package com.example.warehousemanager.Object;

public class ProductBill {
    private String id;
    private String image;
    private String name;
    private int price;
    private int quality;

    public ProductBill() {
    }

    public ProductBill(String id, String image, String name, int price, int quality) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quality = quality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
