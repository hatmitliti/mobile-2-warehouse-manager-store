package com.example.warehousemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanager.User.User;
import com.example.warehousemanager.User.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<User> userArrayList;
    private UserAdapter userAdapter;
    private ListView lvListUser;
    private DatabaseReference databaseReference;
    private Button btnBack,btnThemTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_customer);
        setControll();
        setEvent();
    }

    private void  setEvent() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        context = this;

        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(context,R.layout.items_customer,userArrayList);
        lvListUser.setAdapter(userAdapter);

        databaseReference.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userArrayList.add(dataSnapshot.getValue(User.class));
                userAdapter.notifyDataSetChanged();
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnThemTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,create_account_customer.class);
                startActivity(intent);
            }
        });
    }
    private void setControll() {
        lvListUser = findViewById(R.id.lvListUser);
        btnBack = findViewById(R.id.backLayoutCustomer);
        btnThemTaiKhoan = findViewById(R.id.btnThemTaiKhoan);
    }
}