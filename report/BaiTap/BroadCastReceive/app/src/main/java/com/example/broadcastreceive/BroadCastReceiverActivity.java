package com.example.broadcastreceive;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.Manifest;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broadcastreceive.admin.LockScreenAdminReceiver;
import com.example.broadcastreceive.recevies.SMSReceiver;
import com.squareup.picasso.Picasso;

public class BroadCastReceiverActivity extends AppCompatActivity {

    private int REQUEST_CODE = 1, PICK_CONTACT = 2;
    private TextView txtContact;
    private IntentFilter filter;

    private SMSReceiver smsReceiver;
    public static String SMS_RECEIVE_ACTION = "android.provider.Telephony.SMS_RECEIVED";


    // FOR admin permission
    private static DevicePolicyManager devicePolicyManager;
    private static ComponentName componentName;


    // start activity forresult
    ActivityResultLauncher secondActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_SHORT).show();
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri contactUri = result.getData().getData();
                        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                            String phone = cursor.getString(phoneIndex);
                            String name = cursor.getString(nameIndex);

                            txtContact.setText(name + " - " + phone);

                        }
                    }

                }
            });


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(getApplicationContext(), LockScreenAdminReceiver.class);
        setContentView(R.layout.broadcast_receivcer_example);

        //
        Intent adminIntent  = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        adminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        startActivity(adminIntent);


        if (!checkPermission(Manifest.permission.CALL_PHONE) || !checkPermission(Manifest.permission.RECEIVE_SMS)) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS}, REQUEST_CODE);
        } else {
            doMain();

        }


    }

    // check permission:
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission(String permission) {
        boolean ok = false;

        int check = checkSelfPermission(permission);
        if (check == PackageManager.PERMISSION_GRANTED) {
            ok = true;
        }

        return ok;
    }

    private void doMain() {

        smsReceiver = new SMSReceiver();
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        txtContact = findViewById(R.id.txtPick);
        Button btnMackCall = findViewById(R.id.btnMackCall);
        Button btnGetContact = findViewById(R.id.btnGetContact);
        btnMackCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:099999999"));
                startActivity(intent);
            }
        });


        btnGetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // conjig

                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

                // check if the phone have app contact is installed
                PackageManager packageManager = getPackageManager();
                ComponentName componentName = intent.resolveActivity(packageManager);
                if (componentName == null) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa cài ứng dụng để mở", Toast.LENGTH_SHORT).show();
                } else {
                    secondActivity.launch(intent);

                    // startActivityForResult(intent, PICK_CONTACT);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Button btnNice = findViewById(R.id.btnMackCall);
                btnNice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 0912999999"));
                        startActivity(intent);
                    }
                });
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


   public   static  void  lockScreen(){
        boolean adminActive = devicePolicyManager.isAdminActive(componentName);
        if (adminActive){
            devicePolicyManager.lockNow();
        }
   }


    @Override
    protected void onResume() {  super.onResume();
        registerReceiver(smsReceiver, filter);

    }

    @Override
    protected void onStop() {
        unregisterReceiver(smsReceiver);
        super.onStop();

    }
}