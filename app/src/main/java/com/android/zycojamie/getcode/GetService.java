package com.android.zycojamie.getcode;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetService extends Service {
    private String SMS_URI="content://sms/";
    private SMSObserver smsObserver;

    public GetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public int onStartCommand(Intent intent,int flags,int id){
        Uri uri=Uri.parse(SMS_URI);
        smsObserver=new SMSObserver(handler);
        getContentResolver().registerContentObserver(uri,true,smsObserver);
        return START_STICKY;
    }
    public class SMSObserver extends ContentObserver {
        public SMSObserver(Handler handler){
            super(handler);
        }
        public void onChange(boolean a){
            super.onChange(a);
            StringBuilder sbuilder=new StringBuilder();
            StringBuilder sbuilder2=new StringBuilder();
            Uri uri=Uri.parse(SMS_URI);
            Cursor cursor=getContentResolver().query(uri,new String[]{"body"},"read=?",new String[]{"0"},null);
            if(cursor.moveToFirst()){
                do{
                    String body=cursor.getString(cursor.getColumnIndex("body"));
                    sbuilder.append(body);
                }while(cursor.moveToNext());
            }
            String sms=sbuilder.toString();
            Pattern pattern=Pattern.compile("[0-9]{3,}");
            Matcher matcher=pattern.matcher(sms);
            if(matcher.find()){
                sbuilder2.append(matcher.group());
            }
            ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null,sbuilder2.toString()));
            Toast.makeText(getApplicationContext(),"已将验证码复制到剪贴板",Toast.LENGTH_SHORT).show();
            sbuilder.setLength(0);
            sbuilder2.setLength(0);
            cursor.close();
        }
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public void onDestroy(){
        super.onDestroy();
        if(smsObserver!=null){
            getContentResolver().unregisterContentObserver(smsObserver);
        }
    }
}
