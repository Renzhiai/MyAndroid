package com.rza.powerconsumption.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

public class MemUtil {
    //根据pid获取内存信息
    public static void getMeminfoByPid(int[] pids, Context ctx){
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        Debug.MemoryInfo[] mis=am.getProcessMemoryInfo(pids);
        for (Debug.MemoryInfo mi:mis){
            Log.d("rza","TPSS="+mi.getTotalPss());
        }
    }

    //展示内存信息
    private void GetMemoryInfo(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mInfo);
        Log.d("rza","" + (mInfo.totalMem / 1024) + "KB    " + (mInfo.totalMem / 1024 / 1024) + "MB");
        Log.d("rza","" + (mInfo.totalMem - mInfo.availMem) * 100 / mInfo.totalMem + "%");
        Log.d("rza", "系统剩余内存:" + (mInfo.availMem >> 10) + "k");
    }
}