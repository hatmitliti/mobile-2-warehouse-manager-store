package com.example.warehousemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanager.Adapter.AdapterProductBill;
import com.example.warehousemanager.Object.Bill;
import com.example.warehousemanager.Object.ProductBill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailBill extends AppCompatActivity {
    TextView txtNameChiTietDonHang;
    TextView txtPhoneChiTietDonHang;
    TextView txtAddressChiTietDonHang;
    Button btnDienThoai;
    TextView txtTienThuChiTietDonHang;
    Button btnFail;
    Button btnCompleted;
    ArrayList<ProductBill> list;
    AdapterProductBill adapterProductBill;
    ListView lvDetailBill;
    String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        list = new ArrayList<ProductBill>();
        adapterProductBill = new AdapterProductBill(getApplicationContext(), R.layout.item_product_bill, list);
        lvDetailBill = findViewById(R.id.lvDetailBill);
        lvDetailBill.setAdapter(adapterProductBill);

        txtNameChiTietDonHang = findViewById(R.id.txtNameChiTietDonHang);
        txtPhoneChiTietDonHang = findViewById(R.id.txtPhoneChiTietDonHang);
        txtAddressChiTietDonHang = findViewById(R.id.txtAddressChiTietDonHang);

        btnDienThoai = findViewById(R.id.btnDienThoai);
        txtTienThuChiTietDonHang = findViewById(R.id.txtTienThuChiTietDonHang);
        btnFail = findViewById(R.id.btnFail);
        btnCompleted = findViewById(R.id.btnCompleted);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bills").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getId().equals(getIntent().getStringExtra("id"))) {
                    txtNameChiTietDonHang.setText(bill.getName());
                    txtPhoneChiTietDonHang.setText(bill.getPhone());
                    phone = bill.getPhone();
                    txtAddressChiTietDonHang.setText(bill.getAddress());
                    txtTienThuChiTietDonHang.setText(bill.getTotalMoney() + "");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // lấy các sản phẩm bill đó:
        databaseReference.child("bill_detail").child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ProductBill productBill = dataSnapshot.getValue(ProductBill.class);
                list.add(productBill);
                adapterProductBill.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // bấm nút điện thoại để gọi người đó
        btnDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                startActivity(intent);
            }
        });

        // bấm nút hủy:
        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                FirebaseDatabase.getInstance().getReference("bills").child(getIntent().getStringExtra("id")).removeValue();
                                FirebaseDatabase.getInstance().getReference("bill_detail").child(getIntent().getStringExtra("id")).removeValue();
                                Toast.makeText(getApplicationContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailBill.this);
                builder.setMessage("Bạn thực sự muốn hủy?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        // bấm nút đồng ý:
        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("bills").child(getIntent().getStringExtra("id")).child("status").setValue(1);


                onBackPressed();
            }
        });
        // toolbarr
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}