package com.example.warehousemanager.Category;

public class Category {
    private String id;
    private String name;

    public Category() {
    }

    public Category(String id, String name) {

        this.id = id;
        this.name = name;
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

    public void setName(String nameCategory) {
        this.name = nameCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", nameCategory='" + name + '\'' +
                '}';
    }
}
