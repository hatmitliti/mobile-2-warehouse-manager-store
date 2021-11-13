package com.example.warehousemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private Button btnThemTaiKhoan,btnTim;
    private ArrayList<String> mkey = new ArrayList<>();
    private EditText edtTim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_customer);



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
                mkey.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = mkey.indexOf(dataSnapshot.getKey());
                userArrayList.set(index,dataSnapshot.getValue(User.class));
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.remove(dataSnapshot.getValue(User.class));
                userAdapter.notifyDataSetChanged();
                mkey.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnThemTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,create_account_customer.class);
                startActivity(intent);
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTim.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Vui Lòng Nhập Dữ Liệu Trước Khi Tìm Kiếm", Toast.LENGTH_SHORT).show();
                }else
                {
                    userArrayList.clear();
                    userAdapter.notifyDataSetChanged();
                    databaseReference.child("user").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if(dataSnapshot.getValue(User.class).getName().contains(edtTim.getText().toString()))
                            {
                                userArrayList.add(dataSnapshot.getValue(User.class));
                                userAdapter.notifyDataSetChanged();
                                mkey.add(dataSnapshot.getKey());
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            int index = mkey.indexOf(dataSnapshot.getKey());
                            userArrayList.set(index,dataSnapshot.getValue(User.class));
                            userAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            userArrayList.remove(dataSnapshot.getValue(User.class));
                            userAdapter.notifyDataSetChanged();
                            mkey.remove(dataSnapshot.getKey());
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    private void setControll() {
        lvListUser = findViewById(R.id.lvListUser);
        btnThemTaiKhoan = findViewById(R.id.btnThemTaiKhoan);
        edtTim = findViewById(R.id.edtTimUser);
        btnTim = findViewById(R.id.btnTimUser);
    }
}