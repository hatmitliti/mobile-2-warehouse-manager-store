package com.example.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class BillActivityCho extends AppCompatActivity {


    AdapterBill adapterBill;
    ListView lvBill;
    ArrayList<Bill> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill_cho);
        lvBill = findViewById(R.id.lvBillCho);
        list = new ArrayList<>();
        adapterBill = new AdapterBill(getApplicationContext(), R.layout.item_listview_don_hang_cho, list);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bills").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() == 0) {
                    list.add(dataSnapshot.getValue(Bill.class));
                    adapterBill.notifyDataSetChanged();
                    //   Toast.makeText(getApplicationContext(), "Alo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() != 0) {
                    list.remove(bill);
                    adapterBill.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                list.remove(bill);
                adapterBill.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lvBill.setAdapter(adapterBill);


        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailBill.class);
                intent.putExtra("id", list.get(i).getId());
                startActivity(intent);
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