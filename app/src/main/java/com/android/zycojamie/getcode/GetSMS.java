package com.android.zycojamie.getcode;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zckya on 2017/5/5.
 */

public class GetSMS extends BroadcastReceiver {
    @TargetApi(24)
    public void onReceive(Context context, Intent intent){
        Bundle bundle=intent.getExtras();
        String format=intent.getStringExtra("format");
        Object[] obj=(Object[]) bundle.get("pdus");
        for(Object object:obj){
            SmsMessage smsMessage=SmsMessage.createFromPdu((byte[])object,format);
            Toast.makeText(context,smsMessage.getDisplayMessageBody(),Toast.LENGTH_SHORT).show();
            Log.d("GetSMS",smsMessage.getDisplayMessageBody());
        }
    }

}
