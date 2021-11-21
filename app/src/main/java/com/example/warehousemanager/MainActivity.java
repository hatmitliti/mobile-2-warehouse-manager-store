package com.example.warehousemanager;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView imgBill, imgNhapHang, imgThongKe, imgKhachHang,
            imgSanPham, imgLoaiSanPham, imgHangSanXuat, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportActionBar().setTitle("Quản lý kho hàng");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_bg_toolbar)));
        setControll();
        setEvent();
    }

    private void setEvent() {
        imgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BillActivity.class);
                startActivity(intent);
            }
        });
        imgNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImportGoodsActivity.class);
                startActivity(intent);
            }
        });
        imgKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });
        imgThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ThongKeActivity.class));
            }
        });
        imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
        imgLoaiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductCategory.class);
                startActivity(intent);
            }
        });
        imgHangSanXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManufacturerProduct.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserVip.class);
                startActivity(intent);
            }
        });
    }

    private void setControll() {
        imgBill = findViewById(R.id.imgBill);
        imgNhapHang = findViewById(R.id.imgImport);
        imgThongKe = findViewById(R.id.imgStatistics);
        imgKhachHang = findViewById(R.id.imgCustomer);
        imgLoaiSanPham = findViewById(R.id.imgCategory);
        imgHangSanXuat = findViewById(R.id.imgManufaturer);
        imgSanPham = findViewById(R.id.imgProduct);
        imageView2 = findViewById(R.id.imageView2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finishAffinity();
                break;
        }
        return true;
    }
}