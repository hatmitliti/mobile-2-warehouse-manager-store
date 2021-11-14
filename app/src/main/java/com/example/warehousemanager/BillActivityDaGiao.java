package com.example.warehousemanager;

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
import com.example.warehousemanager.User.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BillActivityDaGiao extends AppCompatActivity {


    AdapterBill adapterBill;
    ListView lvBill;
    ArrayList<Bill> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill_da_giao);
        lvBill = findViewById(R.id.lvBill);
        list = new ArrayList<>();
        adapterBill = new AdapterBill(getApplicationContext(), R.layout.item_listview_don_hang_cho, list);
        lvBill.setAdapter(adapterBill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bills").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() == 3|| bill.getStatus() == 4) {
                    list.add(dataSnapshot.getValue(Bill.class));
                    adapterBill.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill.getStatus() == 3 || bill.getStatus() == 4) {
                    list.add(bill);
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

        lvBill.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // tăng tiền mua cho người dùng:
                if (list.get(i).getStatus() == 4){
                    Toast.makeText(getApplicationContext(), "Bạn đã xác nhận đơn hàng này", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance().getReference("user").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user.getId().equals(list.get(i).getId_user())) {
                                int tongTienBill = list.get(i).getTotalMoney();
                                int tong = tongTienBill + user.getTotalMoney();
                                FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("totalMoney")
                                        .setValue(tong);
                                FirebaseDatabase.getInstance().getReference("bills").child(list.get(i).getId()).child("status").setValue(4);
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
                }
                return true;
            }
        });

    }
}