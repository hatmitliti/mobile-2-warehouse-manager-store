package com.example.warehousemanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanager.Adapter.AdapterBill;

public class BillActivity extends AppCompatActivity {


    AdapterBill adapterBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill);

    }
}