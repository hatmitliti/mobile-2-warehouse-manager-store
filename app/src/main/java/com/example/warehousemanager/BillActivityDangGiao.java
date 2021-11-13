package com.example.warehousemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.warehousemanager.Adapter.AdapterBill;
import com.example.warehousemanager.Object.Bill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BillActivityDangGiao extends AppCompatActivity {


    AdapterBill adapterBill;
    ListView lvBill;
    ArrayList<Bill> list;
    Button btnPhucHoi;
    String billPhucHoi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill_dang_giao);
        lvBill = findViewById(R.id.lvBill);
        list = new ArrayList<>();
        adapterBill = new AdapterBill(getApplicationContext(), R.layout.item_listview_don_hang_cho, list);
        lvBill.setAdapter(adapterBill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bills").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() == 2) {
                    list.add(dataSnapshot.getValue(Bill.class));
                    adapterBill.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() == 2) {
                    list.add(bill);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(bill.getId()))
                            if (list.get(i).getId().equals(bill.getId())) {
                                list.remove(bill);
                                billPhucHoi = list.get(i).getId();
                            }
                    }
                }
                adapterBill.notifyDataSetChanged();
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


        btnPhucHoi = findViewById(R.id.btnPhucHoi);
        btnPhucHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("bills").child(billPhucHoi).child("status").setValue(2);
                Toast.makeText(getApplicationContext(), "Phục hồi thành công", Toast.LENGTH_SHORT).show();
                billPhucHoi = "";
                gavityPhucHoi();
            }
        });

        lvBill.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                billPhucHoi = list.get(i).getId();
                FirebaseDatabase.getInstance().getReference("bills").child(list.get(i).getId()).child("status").setValue(3);
                gavityPhucHoi();
                list.remove(i);
                return true;
            }
        });
        gavityPhucHoi();


    }

    public void gavityPhucHoi() {
        if (billPhucHoi.equals("")) {
            btnPhucHoi.setVisibility(View.GONE);
        } else {
            btnPhucHoi.setVisibility(View.VISIBLE);
        }
    }
}