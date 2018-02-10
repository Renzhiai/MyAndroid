package com.rza01.flow;
import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.TrafficStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    TextView tv1;
    Button btn1;
    TextView tv2;
    String path="/proc/net/xt_qtaguid/stats";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tx1);
        btn1 = (Button) findViewById(R.id.btn1);
        tv2=(TextView) findViewById(R.id.tx2);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    },1);
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rza", "bbbb");
                RandomAccessFile
            }
        });
    }

    //第一个方法
    private String getTotalBytesManual (int uid){
        String path="/init.rc";
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
//            StringBuffer tempString = new StringBuffer();
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                Log.d("rza","line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
//        tv2.setText(result.toString());
//        Log.d("rza",result.toString());
//        return result.toString();
        return null;
    }


    //第三个方法
    private String getTotalBytesManual3(int uid) {
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 1024;
            StringBuffer output = new StringBuffer();
            char[] buffer = new char[1024];
            while ((line = reader.read(buffer)) != 0) {
                Log.d("rza","line="+line);
                output.append(buffer, 0, line);
            }
            Log.d("rza",output.toString());
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length >0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //用户同意授权
        }else{
            //用户拒绝授权
        }
    }
}

