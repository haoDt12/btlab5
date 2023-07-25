package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab5.Activity.AddActivity;
import com.example.lab5.Activity.ViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnView;
    private Button btnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnView = (Button) findViewById(R.id.btn_view);
        btnNew = (Button) findViewById(R.id.btn_new);
        btnView.setOnClickListener(this);
        btnNew.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_view){
            startActivity(new Intent(this, ViewActivity.class));
        } else if (view.getId() == R.id.btn_new) {
            startActivity(new Intent(this, AddActivity.class));
        }
    }
}