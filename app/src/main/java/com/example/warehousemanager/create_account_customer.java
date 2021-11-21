package com.example.warehousemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.warehousemanager.Product.Product;
import com.example.warehousemanager.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class create_account_customer extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView imgUser;
    private EditText edtEmail,edtPassword,edtRePassword,edtNameUser,edtPhone,edtAddress;
    private Button btnTaoTaiKhoan;
    FirebaseAuth firebaseAuth;
    private int RESULT_LOAD_IMAGE = 1;
    private Context context;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_account_customer);

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
    private void setEvent(){

        context = this;
        //
        firebaseAuth = FirebaseAuth.getInstance();
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

        btnTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable.ConstantState resDrawable = getResources().getDrawable(R.drawable.user_icon).getConstantState();
                Drawable.ConstantState resImg = imgUser.getDrawable().getConstantState();
                if(edtEmail.getText().toString().equals("") ||edtPassword.getText().toString().equals("") || edtRePassword.getText().toString().equals("") || edtPhone.getText().toString().equals("")
                || edtNameUser.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Vui Lòng Nhập Đầy Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                }
                else if(!edtRePassword.getText().toString().equals(edtPassword.getText().toString()))
                {
                    Toast.makeText(context, "Vui Lòng Kiểm Tra Lại RePassword", Toast.LENGTH_SHORT).show();
                }
                else if(resImg.toString().equals(resDrawable.toString())) {
                    Toast.makeText(context, "Vui Lòng Chọn Ảnh", Toast.LENGTH_SHORT).show();
                }
                else{
                    DangKy();
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

    private void DangKy(){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(create_account_customer.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                           addValueDataBaseRealTime();
                       }else
                       {
                           Toast.makeText(create_account_customer.this, "Đăng Ký Không Thành Công", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }
    private void addValueDataBaseRealTime(){
        //
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://warehouse-manager-1bfc1.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        //
        Calendar calendar = Calendar.getInstance();
        final String imageName = "image" + calendar.getTimeInMillis() + ".png";
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("ImagesUsers/"+imageName);
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
                                String id = firebaseAuth.getUid();
                                String email = edtEmail.getText().toString();
                                String password = edtPassword.getText().toString();
                                String name = edtNameUser.getText().toString();
                                String phone = edtPhone.getText().toString();
                                String address = edtAddress.getText().toString();
                                String imageURL = uri.toString();
                                //createNewPost(imageUrl);
                                User user = new User(id,name,email,phone,"Đồng",address,imageURL,imageName,0);
                                databaseReference.child("user").child(firebaseAuth.getUid()).setValue(user);
                                Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show();
                                setTextEmpty();
                            }
                        });
                    }
                }
            }
        });

    }
    private void setTextEmpty(){
        imgUser.setImageResource(R.drawable.user_icon);
        edtAddress.setText("");
        edtPhone.setText("");
        edtNameUser.setText("");
        edtPassword.setText("");
        edtRePassword.setText("");
        edtEmail.setText("");

    }
    private void setControll(){
        edtEmail = findViewById(R.id.edtEmailLayoutCreateUser);
        edtPassword = findViewById(R.id.edtPasswordLayoutCreateUser);
        edtRePassword = findViewById(R.id.edtRePasswordLayoutCreateUser);
        edtNameUser = findViewById(R.id.edtNameUserLayoutCreateUser);
        edtPhone = findViewById(R.id.edtPhoneUserLayoutCreateUser);
        edtAddress = findViewById(R.id.edtAddressUserLayoutCreateUser);
        btnTaoTaiKhoan = findViewById(R.id.btnTaoTaiKhoanNguoiDung);
        imgUser = findViewById(R.id.imgCreateUser);
    }

}