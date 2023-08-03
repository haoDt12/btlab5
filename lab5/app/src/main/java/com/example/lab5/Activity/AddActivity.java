package com.example.lab5.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtDescription;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       if(view.getId()==R.id.btn_add){
           String name = edtName.getText().toString().trim();
           String price = edtPrice.getText().toString().trim();
           String description = edtDescription.getText().toString().trim();
           if (name.isEmpty() || price.isEmpty() || description.isEmpty()){
               Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
           }else {
               addNewData(name, Double.parseDouble(price),description);
               finish();
           }
       }
    }
    private void addNewData(String name, Double price, String description) {
        ProductModel product = new ProductModel();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        ApiService.apiService.addProduct(product).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(AddActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    ProductModel tableItems = response.body();
                    TableListAdapter adapter = new TableListAdapter(new ArrayList<>(),AddActivity.this);
                    if (tableItems != null) {
                        adapter.setTableItems(tableItems);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // Xử lý lỗi khi thêm dữ liệu
                    Toast.makeText(AddActivity.this, "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                Log.d("loi", "onFailure: "+t);
            }
        });
    }
}