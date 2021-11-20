package com.example.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstLoad extends AppCompatActivity {
//    Animation topAnim, bottomAnim;
//    ImageView image;
//    TextView nhom6, slogan;
//    LinearLayout lnName;
    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.first_load);

//        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//
//        image = findViewById(R.id.imageView);
//        nhom6 = findViewById(R.id.nhom6);
//        lnName = findViewById(R.id.lnName);
//
//        image.setAnimation(topAnim);
//        nhom6.setAnimation(topAnim);
//        lnName.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checklogin();
                finish();
            }
        },SPLASH_SCREEN);


    }

    private void checklogin() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth != null){
            startActivity(new Intent(FirstLoad.this,MainActivity.class));
        }else {
            startActivity(new Intent(FirstLoad.this,LoginActivity.class));
        }
    }
}
