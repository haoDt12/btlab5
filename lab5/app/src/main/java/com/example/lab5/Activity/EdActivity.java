package com.example.lab5.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5.Adapter.TableListAdapter;
import com.example.lab5.Api.ApiService;
import com.example.lab5.MainActivity;
import com.example.lab5.Model.ProductModel;
import com.example.lab5.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtDescription;
    private Button btnEdit;
    private Button btnDelete;
    private ProductModel model;
    private TableListAdapter adapter; // Khai báo adapter
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id =  intent.getStringExtra("id");
        String name =  intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String description =  intent.getStringExtra("description");
        edtName.setText(name);
        edtPrice.setText(String.valueOf(price));
        edtDescription.setText(description);
        new ProductModel();

        model = new ProductModel();model.setId(id);
        model.setName(name);
        model.setPrice(Double.parseDouble(price));
        model.setDescription(description);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


    }

    private void updateProduct(String id, String name, Double price, String description) {
        ProductModel productModel = new ProductModel();
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setDescription(description);


        Call<ProductModel> call = ApiService.apiService.updateProduct(id, productModel);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EdActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        ProductModel tableItems = response.body();
                        TableListAdapter adapter = new TableListAdapter(new ArrayList<>(), EdActivity.this);
                        if (tableItems != null) {
                            adapter.setTableItems(tableItems);
                            adapter.notifyDataSetChanged();
                            startActivity(new Intent(getBaseContext(),ViewActivity.class));
                        }
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }
    private void deleteProduct(ProductModel model) {
        Object id = model.getId();
        Call<ProductModel> call = ApiService.apiService.deleteProduct(id);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EdActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    ProductModel tableItems = response.body();
                    TableListAdapter adapter = new TableListAdapter(new ArrayList<>(), EdActivity.this);
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                        startActivity(new Intent(getBaseContext(),ViewActivity.class));
                    }

                } else {
                    Log.d("MAIN", "Response Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("MAIN", "Response Fail" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit){
            String name = edtName.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            if (name.isEmpty() || price.isEmpty() || description.isEmpty()){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else{
                updateProduct((String) model.getId(), name, Double.parseDouble(price), description);
            }
        } else if (view.getId() == R.id.btn_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xóa sản phẩm");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                deleteProduct(model);
            });
            builder.setNegativeButton("Hủy", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}