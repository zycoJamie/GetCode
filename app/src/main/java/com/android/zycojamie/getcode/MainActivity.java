package com.android.zycojamie.getcode;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECEIVE_SMS},1);
        }
        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);


    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.start:
                Intent intent=new Intent(MainActivity.this,GetService.class);
                startService(intent);
                Toast.makeText(MainActivity.this,"功能已经开启啦",Toast.LENGTH_SHORT).show();
                break;
            case R.id.stop:
                Intent intent1=new Intent(MainActivity.this,GetService.class);
                stopService(intent1);
                Toast.makeText(MainActivity.this,"为什么要关闭功能~55555",Toast.LENGTH_SHORT).show();
                break;
            default:;

        }
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult){
        switch(requestCode){
            case 1:
                if(grantResult!=null&& grantResult[0]==PackageManager.PERMISSION_GRANTED){

                }
                break;
            default:;
        }
    }

}
