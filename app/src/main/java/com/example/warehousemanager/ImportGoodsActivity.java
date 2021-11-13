package com.example.warehousemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.warehousemanager.Adapter.AdapterImportProduct;
import com.example.warehousemanager.Product.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ImportGoodsActivity extends AppCompatActivity {

    ArrayList<Product> listProductFull;
    ArrayList<String> listProductString;
    ArrayList<Integer> listProductQualityImport;
    ArrayList<String> listProductIDImport;
    ArrayList<String> listProductStringImport;
    Spinner spTenSachNhapHang;
    EditText edtSoLuongNhapHang;
    Button btnThemNhapHang;
    Button btnNhapHang;
    ListView lvNhapHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_import_goods);


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


        setControl();
        getDataProduct();


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listProductString);
        spTenSachNhapHang.setAdapter(adapter);
        listProductQualityImport = new ArrayList<>();
        listProductIDImport = new ArrayList<>();
        listProductStringImport = new ArrayList<>();

        AdapterImportProduct adapterImportProduct = new AdapterImportProduct(this, R.layout.thu_kho_xuat_hang_item, listProductQualityImport, listProductIDImport, listProductStringImport);
        lvNhapHang.setAdapter(adapterImportProduct);

        btnThemNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spTenSachNhapHang.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa chọn", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtSoLuongNhapHang.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Bạn chưa nhập số lượng", Toast.LENGTH_SHORT).show();
                    } else {
                        int quality = Integer.parseInt(edtSoLuongNhapHang.getText().toString());
                        Product product = listProductFull.get(spTenSachNhapHang.getSelectedItemPosition());
                        listProductQualityImport.add(quality);
                        listProductIDImport.add(product.getId());
                        listProductStringImport.add(product.getTenSanPham());
                        adapterImportProduct.notifyDataSetChanged();
                    }
                }
            }
        });

        // bấm vào lâu sẽ xóa sp đó ra:
        lvNhapHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listProductQualityImport.remove(i);
                listProductIDImport.remove(i);
                listProductStringImport.remove(i);
                adapterImportProduct.notifyDataSetChanged();
                return true;
            }
        });



        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listProductStringImport.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Chưa điền", Toast.LENGTH_SHORT).show();
                } else {
                    if (listProductFull.size() != 0) {
                        // nhập hàng
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
                        for (int j = 0; j < listProductIDImport.size(); j++) {
                            int quality = getQuality(listProductIDImport.get(j));
                            if (quality == -1) {

                            } else {
                                mDatabase.child(listProductIDImport.get(j)).child("stock").setValue(quality + listProductQualityImport.get(j));
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Xong", Toast.LENGTH_SHORT).show();
                        listProductQualityImport.clear();
                        listProductIDImport.clear();
                        listProductStringImport.clear();
                        adapterImportProduct.notifyDataSetChanged();
                        onBackPressed();
                    }
                }
            }
        });

    }

    public int getQuality(String id) {
        for (int j = 1; j < listProductFull.size(); j++) {
            if (listProductFull.get(j).getId().equals(id)) {
                return listProductFull.get(j).getStock();
            }
        }
        return -1;
    }

    private void setControl() {
        spTenSachNhapHang = findViewById(R.id.spTenSachNhapHang);
        edtSoLuongNhapHang = findViewById(R.id.edtSoLuongNhapHang);
        btnThemNhapHang = findViewById(R.id.btnThemNhapHang);
        lvNhapHang = findViewById(R.id.lvNhapHang);
        btnNhapHang = findViewById(R.id.btnNhapHang);
    }

    private void getDataProduct() {
        listProductFull = new ArrayList<>();
        listProductString = new ArrayList<>();

        listProductFull.add(null);
        listProductString.add("");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product pd = snapshot.getValue(Product.class);
                pd.setId(snapshot.getKey());
                listProductFull.add(pd);
                listProductString.add(pd.getTenSanPham());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
