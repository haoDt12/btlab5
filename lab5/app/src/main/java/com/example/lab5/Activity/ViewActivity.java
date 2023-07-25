package com.example.lab5.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lab5.Adapter.TableListAdapter;
import com.example.lab5.Api.ApiService;
import com.example.lab5.MainActivity;
import com.example.lab5.Model.ProductModel;
import com.example.lab5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewActivity extends AppCompatActivity{
    private RecyclerView rcvProduct;
    private TableListAdapter adapter;
    private List<ProductModel> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mList = new ArrayList<>();
        rcvProduct = (RecyclerView) findViewById(R.id.rcv_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(itemDecoration);


        adapter = new TableListAdapter(new ArrayList<>(),ViewActivity.this);
        rcvProduct.setAdapter(adapter);
        callApiGetTableList();
    }
    private void callApiGetTableList() {
        // lay danh sach
        ApiService.apiService.getProduct().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductModel> tableItems = response.body();
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ViewActivity.this, "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(ViewActivity.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}