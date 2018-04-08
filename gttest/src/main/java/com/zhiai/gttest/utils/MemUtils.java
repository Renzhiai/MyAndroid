package com.zhiai.gttest.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MemUtils {
    /**
     * 获取正在运行的指定app的信息
     * @param packageName
     * @return appProcessList
     */
    public static List<ProcessUtils.ProcessInfo> getAllRunningAppProcessInfo(String packageName) {
        List<ProcessUtils.ProcessInfo> appProcessList = new ArrayList<ProcessUtils.ProcessInfo>();
        BufferedReader reader = null;
        try {
            ProcessBuilder execBuilder = new ProcessBuilder("sh", "-c", "ps | grep "+packageName);
            execBuilder.redirectErrorStream(true);
            Process exec = execBuilder.start();
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            if (reader.readLine() == null){
                return null;
            }
            while ((line = reader.readLine()) != null) {
                String[] array = line.trim().split("\\s+");
                Log.d("rza", "line: "+line);
                if (array.length >= 9) {
                    int uid = Integer.parseInt(array[0].substring(4)) + 10000;
                    int pid = Integer.parseInt(array[1]);
//                    Log.d("rza", "uid: "+uid);
                    Log.d("rza", "pid: "+pid);
                    int ppid = Integer.parseInt(array[2]);
                    ProcessUtils.ProcessInfo pi = new ProcessUtils.ProcessInfo(pid, array[8], ppid, uid);
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

    /**
     * 获取app的PID
     * @param pName
     * @return pid
     */
    public static List<Integer> getProcessPID(Context context,String pName) {
        int pId = -1;
        List<Integer> pIds = new ArrayList<>();
        List<ProcessUtils.ProcessInfo> appProcessInfos = getAllRunningAppProcessInfo(pName);
        if (appProcessInfos == null){
            Toast.makeText(context,"请确认包名正确或已启动该App",Toast.LENGTH_SHORT).show();
            return null;
        }
        for (ProcessUtils.ProcessInfo info : appProcessInfos) {
            if (info != null && info.name.contains(pName)) {
                pId = info.pid;
                pIds.add(pId);
//                Log.d("rza", "getProcessPID: " + pId);
//                break;
            }
        }
        return pIds;
    }

    /**
     * 获取PSS信息：getTotalPss 单位KB
     *  效果类似于 adb shell dumpsys meminfo | find "pid"
     * @return TotalPss
     */
    public static List<Integer> getPSS(Context context, List<Integer> pIds) {
        List<Integer> pssList = new ArrayList<>();
        for (Integer pid : pIds){
            int totalPss = 0;
            if (pid >= 0) {
                int[] pids = new int[1];
                pids[0] = pid;
                ActivityManager mAm = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                Debug.MemoryInfo[] memoryInfoArray = mAm.getProcessMemoryInfo(pids);
                Debug.MemoryInfo pidMemoryInfo = memoryInfoArray[0];
                totalPss = pidMemoryInfo.getTotalPss();
                pssList.add(totalPss);
            }
        }
        return pssList;
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
     * 根据AndroidProcesses包来获取recentApp
     */
    public static void getProcessName() {
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
}
