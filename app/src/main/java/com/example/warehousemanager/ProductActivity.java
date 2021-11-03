package com.example.warehousemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanager.Category.Category;
import com.example.warehousemanager.Product.Product;
import com.example.warehousemanager.Product.ProductAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {

    private Spinner spnHang, spnLoai;
    private Context context;
    private ArrayList<String> mkeySPNLoai = new ArrayList<>(), mKeySPNHang = new ArrayList<>(), mkey = new ArrayList<>();
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private ListView lvProduct;
    private Product productSelected;
    private EditText edtTenSP, edtGiaSP;
    private ImageView imgAnhSP;
    private Button btnThem, btnSua,btnXoa,btnBack;
    private int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product);
        setControll();
        SetEvent();
    }

    private void SetEvent() {
        /*
         * Tạo các biến để lưu file ảnh trên firebase
         * */
        //
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://warehouse-manager-1bfc1.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        //
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //
        context = this;
        //
        ArrayList<String> dataSpinnerHang = new ArrayList<>();
        ArrayList<String> dataSpinnerLoai = new ArrayList<>();
        //
        ArrayAdapter<String> adapterHang = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataSpinnerHang);
        ArrayAdapter<String> adapterLoai = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataSpinnerLoai);
        //
        adapterLoai.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        adapterHang.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        //
        spnHang.setAdapter(adapterHang);
        spnLoai.setAdapter(adapterLoai);
        //
        databaseReference.child("categorys").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSpinnerLoai.add(dataSnapshot.getValue(Category.class).getName());
                mkeySPNLoai.add(dataSnapshot.getKey());
                adapterLoai.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = mkeySPNLoai.indexOf(dataSnapshot.getKey());
                dataSpinnerLoai.set(index, dataSnapshot.getValue(Category.class).getName());
                adapterLoai.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                dataSpinnerLoai.remove(dataSnapshot.getValue(Category.class).getName());
                mkeySPNLoai.remove(dataSnapshot.getKey());
                adapterLoai.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //
        databaseReference.child("manufacturer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSpinnerHang.add(dataSnapshot.getValue(Category.class).getName());
                mKeySPNHang.add(dataSnapshot.getKey());
                adapterHang.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = mKeySPNHang.indexOf(dataSnapshot.getKey());
                dataSpinnerHang.set(index, dataSnapshot.getValue(Category.class).getName());
                adapterHang.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                dataSpinnerHang.remove(dataSnapshot.getValue(Category.class).getName());
                mKeySPNHang.remove(dataSnapshot.getKey());
                adapterHang.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //
        productAdapter = new ProductAdapter(context, R.layout.items_product, productArrayList);
        lvProduct.setAdapter(productAdapter);
        databaseReference.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product = dataSnapshot.getValue(Product.class);
                productArrayList.add(product);
                productAdapter.notifyDataSetChanged();
                mkey.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int index = mkey.indexOf(dataSnapshot.getKey());
                productArrayList.set(index, dataSnapshot.getValue(Product.class));
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                productArrayList.remove(dataSnapshot.getValue(Product.class));
                productAdapter.notifyDataSetChanged();
                mkey.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productSelected = productArrayList.get(i);
                edtGiaSP.setText(productSelected.getGiaTien() + "");
                edtTenSP.setText(productSelected.getTenSanPham());
                int indexHang = dataSpinnerHang.indexOf(productSelected.getManufacturer());
                spnHang.setSelection(indexHang);
                int indexLoai = dataSpinnerLoai.indexOf(productSelected.getCategory());
                spnLoai.setSelection(indexLoai);
                Picasso.get().load(productSelected.getHinhAnh()).into(imgAnhSP);
            }
        });
        imgAnhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.putExtra("crop", "true");
                    intent.putExtra("scale", true);
                    intent.putExtra("outputX", 256);
                    intent.putExtra("outputY", 256);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenSP.getText().toString().equals("") || edtGiaSP.getText().equals("")) {
                    Toast.makeText(context, "Vui Lòng Nhập Đầy Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                } else if(!checkLoop(edtTenSP.getText().toString()))
                {
                    Toast.makeText(context, "Tên Sản Phẩm Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    String imageName = "image" + calendar.getTimeInMillis() + ".png";
                    // Create a reference to "mountains.jpg"
                    StorageReference mountainsRef = storageRef.child(imageName);
                    // Get the data from an ImageView as bytes
                    imgAnhSP.setDrawingCacheEnabled(true);
                    imgAnhSP.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgAnhSP.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Thêm Sản Phẩm", Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String idProduct = "s" + (mkey.size() + 1);
                                            String tenSP = edtTenSP.getText().toString();
                                            int giaSP = Integer.parseInt(edtGiaSP.getText().toString());
                                            String loaiSP = spnLoai.getSelectedItem().toString();
                                            String HangSP = spnHang.getSelectedItem().toString();
                                            String imageURL = uri.toString();
                                            //createNewPost(imageUrl);
                                            Product product = new Product(imageURL, imageName, idProduct, tenSP, giaSP, loaiSP, 0, 0, HangSP);
                                            databaseReference.child("products").child(idProduct).setValue(product);
                                            Toast.makeText(context, "Thêm Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                                            setTextEmpty();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenSP.getText().toString().equals("") || edtGiaSP.getText().equals("")) {
                    Toast.makeText(context, "Vui Lòng Nhập Đầy Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar calendar = Calendar.getInstance();
                    String imageName = "image" + calendar.getTimeInMillis() + ".png";
                    // Create a reference to "mountains.jpg"
                    StorageReference mountainsRef = storageRef.child(imageName);
                    // Get the data from an ImageView as bytes
                    imgAnhSP.setDrawingCacheEnabled(true);
                    imgAnhSP.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgAnhSP.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Thêm Sản Phẩm", Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String idProduct = productSelected.getId();
                                            String tenSP = edtTenSP.getText().toString();
                                            int giaSP = Integer.parseInt(edtGiaSP.getText().toString());
                                            String loaiSP = spnLoai.getSelectedItem().toString();
                                            String HangSP = spnHang.getSelectedItem().toString();
                                            String imageURL = uri.toString();
                                            //createNewPost(imageUrl);
                                            Product product = new Product(imageURL, imageName, idProduct, tenSP, giaSP, loaiSP, productSelected.getStock(), productSelected.getSold(), HangSP);
                                            StorageReference desertRef = storageRef.child(productSelected.getTenHinhAnh());
                                            desertRef.delete();
                                            HashMap hashMap = new HashMap();
                                            hashMap.put(idProduct,product);

                                            databaseReference.child("products").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(Object o) {
                                                    Toast.makeText(context, "Sửa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Sửa Sản Phẩm Không Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            setTextEmpty();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).
                        setTitle("RemoveDataOnFireBase")
                        .setMessage("Bạn Có Chắc Chắn Muốn Xóa Sản Phẩm Này Không")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                                databaseReference.child("products").child(productSelected.getId()).removeValue();
                                StorageReference desertRef = storageRef.child(productSelected.getTenHinhAnh());
                                desertRef.delete();
                                //
                                productArrayList.remove(productSelected);
                                productAdapter.notifyDataSetChanged();
                                //
                                setTextEmpty();
                                productSelected = null;
                            }
                        }).setNegativeButton("Không", null).show();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkLoop(String nameProduct){
        for (Product a:productArrayList
             ) {
            if(a.getTenSanPham().toString().equals(nameProduct))
            {
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri imageUri = data.getData();
            imgAnhSP.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setTextEmpty() {
        edtGiaSP.setText("");
        edtTenSP.setText("");
        imgAnhSP.setImageResource(R.drawable.ic_baseline_image_24);
    }

    private void setControll() {
        spnHang = findViewById(R.id.spnManufacturerLayoutProduct);
        spnLoai = findViewById(R.id.spnCategoryLayoutProduct);
        lvProduct = findViewById(R.id.lvLayoutProduct);
        edtTenSP = findViewById(R.id.edtTenSPLayoutProduct);
        edtGiaSP = findViewById(R.id.edtGiaSPLayoutProduct);
        imgAnhSP = findViewById(R.id.imgSPLayoutProduct);
        btnThem = findViewById(R.id.btnThemSPLayoutProduct);
        btnSua = findViewById(R.id.btnSuaSPLayoutProduct);
        btnXoa = findViewById(R.id.btnXoaSPLayoutProduct);
        btnBack = findViewById(R.id.backLayoutProduct);
    }
}