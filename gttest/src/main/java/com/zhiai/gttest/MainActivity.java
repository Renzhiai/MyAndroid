package com.zhiai.gttest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.meminfo);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getMemInfo();
//                getPSS(MainActivity.this,23432);
//                getProcessPID("com.oecommunity.oeshop");
                testss();
            }
        });
    }
    /**
     * 获取内存信息：total、free、buffers、cached，单位MB
     *
     * @return 内存信息
     */
    public static long[] getMemInfo() {
        long memInfo[] = new long[4];
        try {
            Class<?> procClazz = Class.forName("android.os.Process");
            Class<?> paramTypes[] = new Class[] { String.class, String[].class,
                    long[].class };
            Method readProclines = procClazz.getMethod("readProcLines",
                    paramTypes);
            Object args[] = new Object[3];
            final String[] memInfoFields = new String[] { "MemTotal:",
                    "MemFree:", "Buffers:", "Cached:" };
            long[] memInfoSizes = new long[memInfoFields.length];
            memInfoSizes[0] = 30;
            memInfoSizes[1] = -30;
            args[0] = new String("/proc/meminfo");
            args[1] = memInfoFields;
            args[2] = memInfoSizes;
            if (null != readProclines) {
                readProclines.invoke(null, args);
                for (int i = 0; i < memInfoSizes.length; i++) {
                    memInfo[i] = memInfoSizes[i] / 1024;
                }
            }
            Log.d("rza", "getMemInfo: "+memInfo[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memInfo;
    }

    /**
     * 获取PSS信息：nativePss、dalvikPss、getTotalPss 单位KB
     *  效果类似于 adb shell dumpsys meminfo | find "pid"
     * @return TotalPss
     */
    public static long getPSS(Context context, int pid) {
        long[] value = new long[3]; // Natvie Dalvik Total
        if (pid >= 0) {
            int[] pids = new int[1];
            pids[0] = pid;
            ActivityManager mAm = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            Debug.MemoryInfo[] memoryInfoArray = mAm.getProcessMemoryInfo(pids);
            Debug.MemoryInfo pidMemoryInfo = memoryInfoArray[0];

            value[0] = pidMemoryInfo.nativePss;
            value[1] = pidMemoryInfo.dalvikPss;
            value[2] = pidMemoryInfo.getTotalPss();
        } else {
            value[0] = 0;
            value[1] = 0;
            value[2] = 0;
        }
        Log.d("rza", "nativePss: "+value[0]);
        Log.d("rza", "dalvikPss: "+value[1]);
        Log.d("rza", "getTotalPss: "+value[2]);
        return value[2];
    }

    public List<ProcessInfo> getAllRunningAppProcessInfo() {
        List<ProcessInfo> appProcessList = new ArrayList<ProcessInfo>();
        BufferedReader reader = null;
        try {
            ProcessBuilder execBuilder = new ProcessBuilder("sh", "-c", "ps");
            execBuilder.redirectErrorStream(true);
            Process exec = execBuilder.start();
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.trim().split("\\s+");
                Log.d("rza", "line: "+line.toString());
                if (array.length >= 9) {
                    int uid = Integer.parseInt(array[0].substring(4)) + 10000;
                    int pid = Integer.parseInt(array[1]);
//                    Log.d("rza", "uid: "+uid);
//                    Log.d("rza", "pid: "+pid);
                    int ppid = Integer.parseInt(array[2]);
                    ProcessInfo pi = new ProcessInfo(pid, array[8], ppid, uid);
                    appProcessList.add(pi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return appProcessList;
    }

    public int getProcessPID(String pName) {
        int pId = -1;
        List<ProcessInfo> appProcessInfos = getAllRunningAppProcessInfo();
        for (ProcessInfo info : appProcessInfos) {
            if (info.name.equals(pName)) {
                pId = info.pid;
                break;
            }
        }
        Log.d("rza", "getProcessPID: "+pId);
        return pId;
    }

    public static class ProcessInfo {
        public String name; // 进程名
        public int pid;  // PID
        public int ppid; // 父PID
        public int uid; //  UID

        public ProcessInfo(int pid, String name, int ppid, int uid) {
            this.pid = pid;
            this.name = name;
            this.ppid = ppid;
            this.uid = uid;
        }
    }

    public static void closeReader(Reader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testss() {
        try {
            List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
            for (AndroidAppProcess process : processes) {
                // Get some information about the process
                String processName = process.name;
                Stat stat = null;
                stat = process.stat();
                int pid = stat.getPid();
                int parentProcessId = stat.ppid();
                long startTime = stat.stime();
                int policy = stat.policy();
                char state = stat.state();
                Log.d("rza", "testss: "+pid+","+processName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getProcessPID2(String pName) {
        int pId = -1;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessList = am
                .getRunningAppProcesses();
        int pLength = appProcessList.size();
        for (int i = 0; i < pLength; i++) {
            if (appProcessList.get(i).processName.equals(pName)) {
                pId = appProcessList.get(i).pid;
                break;
            }
        }
        return pId;
    }

}
