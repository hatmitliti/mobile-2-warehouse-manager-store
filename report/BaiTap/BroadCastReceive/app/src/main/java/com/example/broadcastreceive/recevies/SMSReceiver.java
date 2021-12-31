package com.example.broadcastreceive.recevies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.broadcastreceive.BroadCastReceiverActivity;

import java.util.StringTokenizer;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context.getApplicationContext(), "SMS called", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equalsIgnoreCase(BroadCastReceiverActivity.SMS_RECEIVE_ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] objects = (Object[]) bundle.get("pdus");
                String fomart = bundle.getString("format");
                final SmsMessage[] messages = new SmsMessage[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], fomart);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
                    }

                    // print mess:
                    String calNumber = messages[i].getDisplayOriginatingAddress();
                    StringTokenizer stringTokenizer = new StringTokenizer(messages[i].getMessageBody());
                    String key = stringTokenizer.nextToken(":");
                    if (key.equalsIgnoreCase("print")) {
                        Toast.makeText(context.getApplicationContext(), "Mess: " + calNumber + " : " +
                                "Content: " + stringTokenizer.nextToken(), Toast.LENGTH_SHORT).show();
                    } else if (key.equalsIgnoreCase("call")) {
                        makeCall(context, stringTokenizer.nextToken());
                    } else if (key.equalsIgnoreCase("lock")) {
                        lockScreen();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

    }

    private void makeCall(Context context, String nextToken) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(nextToken));
        context.startActivity(intent);

    }

    private void lockScreen() {
        BroadCastReceiverActivity.lockScreen();
    }
}