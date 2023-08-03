package com.example.lab5.Model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ProductModel {
    //@SerializedName("_id")
    private Object id; // Use String type for _id field
    private String name;
    private double price;
    private String  description;
    private Date created_at;
    private Date updated_at;


    public ProductModel() {
    }
    public ProductModel(Object id, String name, double price, String description, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getId() {
        Log.d("DEBUG", "Product ID: " + id);
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
