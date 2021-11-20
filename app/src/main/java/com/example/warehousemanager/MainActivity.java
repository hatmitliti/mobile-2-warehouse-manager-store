package com.example.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imgBill, imgNhapHang, imgThongKe, imgKhachHang, imgSanPham, imgLoaiSanPham, imgHangSanXuat, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

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
}