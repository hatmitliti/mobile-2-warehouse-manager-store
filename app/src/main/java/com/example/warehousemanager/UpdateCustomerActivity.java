package com.example.warehousemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warehousemanager.Product.Product;
import com.example.warehousemanager.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateCustomerActivity extends AppCompatActivity {

    private Context context;
    private Button btnCapNhatThongTin, btnBack;
    private EditText edtEmail,edtTen,edtPhone,edtAddress;
    private de.hdodenhof.circleimageview.CircleImageView imgUser;
    private int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_customer);

        setControll();
        setEvent();
    }
    private void setEvent() {
        /*
         * Tạo các biến để lưu file ảnh trên firebase
         * */
        //
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://warehouse-manager-1bfc1.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //
        context = this;
        //
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("UserData");
        //
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtTen.setText(user.getName());
        edtAddress.setText(user.getAddress());
        Picasso.get().load(user.getImgUser()).into(imgUser);
        //

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context,CustomerActivity.class);
                startActivity(intent1);
            }
        });
        //

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        //
        btnCapNhatThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTen.getText().toString().toString().equals("") || edtPhone.getText().toString().equals("") || edtAddress.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui Lòng Nhập Đầy Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar calendar = Calendar.getInstance();
                    String imageName = "image" + calendar.getTimeInMillis() + ".png";
                    // Create a reference to "mountains.jpg"
                    StorageReference mountainsRef = storageRef.child("UserImage/"+imageName);
                    // Get the data from an ImageView as bytes
                    imgUser.setDrawingCacheEnabled(true);
                    imgUser.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgUser.getDrawable()).getBitmap();
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
                                           String email = edtEmail.getText().toString();
                                           String phone = edtPhone.getText().toString();
                                           String address = edtAddress.getText().toString();
                                           String name = edtTen.getText().toString();
                                            String imageURL = uri.toString();
                                            //createNewPost(imageUrl);
                                            User user1 = new User(user.getId(),name,email,phone,user.getRank(),address,imageURL,imageName,user.getTotalMoney());
                                            StorageReference desertRef = storageRef.child("UserImage").child(user.getNameIMGUser());
                                            desertRef.delete();
                                            HashMap hashMap = new HashMap();
                                            hashMap.put(user.getId(),user1);
                                            user.setNameIMGUser(imageName);
                                            databaseReference.child("user").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(Object o) {
                                                    Toast.makeText(context, "Sửa Thông Tin Người Dùng Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Sửa Thông Tin Người Dùng Không Thành Công", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri imageUri = data.getData();
            imgUser.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setControll() {
        btnCapNhatThongTin = findViewById(R.id.btnCapNhatNguoiDung);
        btnBack = findViewById(R.id.backLayoutUpdateUser);
        edtEmail = findViewById(R.id.edtEmailLayoutUpdateUser);
        edtTen = findViewById(R.id.edtNameUserLayoutUpdateUser);
        edtPhone = findViewById(R.id.edtPhoneUserLayoutUpdateUser);
        edtAddress = findViewById(R.id.edtAddressUserLayoutUpdateUser);
        imgUser = findViewById(R.id.imgUpdateUser);
    }
}