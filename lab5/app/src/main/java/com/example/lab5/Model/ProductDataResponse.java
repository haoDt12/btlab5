package com.example.lab5.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDataResponse {
    @SerializedName("success")
    private int success;
    @SerializedName("product")
    private List<ProductModel> data;

    public List<ProductModel> getData() {
        return data;
    }

    public int getSuccess() {
        return success;
    }

}
