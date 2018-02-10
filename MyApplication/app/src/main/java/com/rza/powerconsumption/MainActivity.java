package com.rza.powerconsumption;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.rza.powerconsumption.utils.WindowUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt1;
    Button bt2;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    ListView lv;
    int permission=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        tv1 = (TextView) findViewById(R.id.totalMem);
        tv2 = (TextView) findViewById(R.id.availableMem);
        tv3 = (TextView) findViewById(R.id.usedMem);
        lv = (ListView) findViewById(R.id.lv);

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
                WindowUtils.showPopupWindow(MainActivity.this);
                WindowUtils.getmView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMeminfoByPid();
                    }
                    });
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.hidePopupWindow();
            }
        });
    }

    //展示内存信息
    private void GetMemoryInfo() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mInfo);
        tv1.setText("" + (mInfo.availMem / 1024) + "KB    " + (mInfo.availMem / 1024 / 1024) + "MB");
        tv2.setText("" + (mInfo.totalMem / 1024) + "KB    " + (mInfo.totalMem / 1024 / 1024) + "MB");
        tv3.setText("" + (mInfo.totalMem - mInfo.availMem) * 100 / mInfo.totalMem + "%");
        Log.i("rza", "系统剩余内存:" + (mInfo.availMem >> 10) + "k");
    }

    //根据包名获取pid
    private int getPidByPkg(String pkg) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mRunningProcess = am.getRunningAppProcesses();
        int pid = -1;
        for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess) {
            Log.d("rza", "" + amProcess.processName);
            Log.d("rza",""+amProcess.pid);
        }
    return 0;
    }

    //根据pid获取内存信息
    private void getMeminfoByPid(){
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
        try {
            for (AndroidAppProcess process : processes) {
                String processName = process.name;
                Stat stat = process.stat();
                int pid = stat.getPid();
                Log.d("rza",processName);
                int[] pids=new int[1];
                pids[0]=pid;
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                Debug.MemoryInfo[] mis=am.getProcessMemoryInfo(pids);
                Debug.MemoryInfo mi=mis[0];
                int memSize = mi.dalvikPss;
                Log.d("rza",""+mi.getTotalPss());
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }
    }
}


