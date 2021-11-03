package com.example.warehousemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.warehousemanager.Category.Category;
import com.example.warehousemanager.Category.CategoryAdapter;
import com.example.warehousemanager.ManufacturerAD.Manufacturer;
import com.example.warehousemanager.ManufacturerAD.ManufacturerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManufacturerProduct extends AppCompatActivity {
    private EditText edtTenHang;
    private ListView lvDanhSachHang;
    private Button btnThem,btnXoa,btnSua,btnBack;
    private Context context;
    private ArrayList<Manufacturer> manufacturerProductArrayList;
    private ManufacturerAdapter manufacturerAdapter;
    ArrayList<String> mKey = new ArrayList<>();
    DatabaseReference Datamanufacturer;
    Manufacturer manufacturerSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_manufacturer);
        setControll();
        setEvent();
    }
    private void setEvent() {
        //
        Datamanufacturer = FirebaseDatabase.getInstance().getReference();
        //
        context = this;
        //
        manufacturerProductArrayList = new ArrayList<>();
        manufacturerAdapter = new ManufacturerAdapter(context,R.layout.items_product_manufacturer, manufacturerProductArrayList);
        lvDanhSachHang.setAdapter(manufacturerAdapter);
        //
        Datamanufacturer.child("manufacturer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Manufacturer manufacturer = dataSnapshot.getValue(Manufacturer.class);
                manufacturerProductArrayList.add(manufacturer);
                manufacturerAdapter.notifyDataSetChanged();
                mKey.add(dataSnapshot.getKey());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = mKey.indexOf(dataSnapshot.getKey());
                manufacturerProductArrayList.set(index,dataSnapshot.getValue(Manufacturer.class));
                manufacturerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                manufacturerProductArrayList.remove(dataSnapshot);
                mKey.remove(dataSnapshot.getKey());
                manufacturerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lvDanhSachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                manufacturerSelected = manufacturerProductArrayList.get(i);
                edtTenHang.setText(manufacturerProductArrayList.get(i).getName());
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTenHang.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Vui Lòng Nhập Dữ Liệu Đầy Đủ", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(checkLoopData(edtTenHang.getText().toString()))
                    {
                        String idManufacturer = (manufacturerProductArrayList.size()+1) + "";
                        String name = edtTenHang.getText().toString();
                        Manufacturer manufacturer = new Manufacturer(idManufacturer,name);
                        Datamanufacturer.child("manufacturer").child(idManufacturer).setValue(manufacturer);
                    }else
                    {
                        Toast.makeText(context, "Tên Loại Đồ Uống Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(manufacturerSelected== null)
                {
                    Toast.makeText(context, "Vui Lòng Chọn Đối Tượng Trước Khi Xóa", Toast.LENGTH_SHORT).show();
                }else
                {
                    edtTenHang.setText("");
                    Datamanufacturer.child("manufacturer").child(manufacturerSelected.getId()).setValue(null);
                    manufacturerProductArrayList.remove(manufacturerSelected);
                    mKey.remove(manufacturerSelected.getId());
                    manufacturerAdapter.notifyDataSetChanged();
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTenHang.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Vui Lòng Nhập Dữ Liệu Đầy Đủ", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(checkLoopData(edtTenHang.getText().toString()))
                    {
                        String name = edtTenHang.getText().toString();
                        Datamanufacturer.child("manufacturer").child(manufacturerSelected.getId()).child("name").setValue(name);
                    }else
                    {
                        Toast.makeText(context, "Tên Loại Đồ Uống Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManufacturerProduct.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private Boolean checkLoopData(String data)
    {
        for (Manufacturer a:manufacturerProductArrayList
        ) {
            if(a.getName().toString().equals(data))
            {
                return false;
            }
        }
        return true;
    }
    private void  setControll() {
        edtTenHang = findViewById(R.id.edtTenHangProductManufacturer);
        lvDanhSachHang = findViewById(R.id.lvProductManufacturer);
        btnThem = findViewById(R.id.btnThemProductManufacturer);
        btnXoa = findViewById(R.id.btnXoaProductManufacturer);
        btnSua = findViewById(R.id.btnSuaProductManufacturer);
        btnBack = findViewById(R.id.backProductManufacturer);
    }
}