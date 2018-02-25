package com.rza.powerconsumption;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rza.powerconsumption.utils.AppUtil;
import com.rza.powerconsumption.utils.MemUtil;
import com.rza.powerconsumption.utils.WindowUtil;

public class MainActivity extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    EditText et1;
    int permission=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt1 = (Button) findViewById(R.id.bt1);  //打开悬浮窗
        bt2 = (Button) findViewById(R.id.bt2);  //关闭悬浮窗
        bt3 = (Button) findViewById(R.id.bt3);
        tv1 = (TextView) findViewById(R.id.totalMem);   //
        tv2 = (TextView) findViewById(R.id.availableMem);
        tv3 = (TextView) findViewById(R.id.usedMem);
        tv4 = (TextView) findViewById(R.id.appMem);
        et1 = (EditText) findViewById(R.id.pid);

        permission = ContextCompat.checkSelfPermission(this,Manifest.permission.SYSTEM_ALERT_WINDOW);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //表示已经授权
        }else{
            ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
        }

        //如果在Activity中申请权限可以的调用：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv4.setText(""+MemUtil.getMeminfoByPid(AppUtil.getPid(et1),MainActivity.this)+"KB");
//                WindowUtil.showPopupWindow(MainActivity.this);
//                WindowUtil.getmView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MemUtil.getMeminfoByPid(AppUtil.getPid(et1),MainActivity.this);
//                    }
//                    });
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtil.hidePopupWindow();
            }
        });
    }








}


