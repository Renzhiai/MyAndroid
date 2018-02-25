package com.rza.powerconsumption.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MemUtil {
    //根据pid获取内存信息
    public static int getMeminfoByPid(int[] pids, Context ctx){
        int mem=0;
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        Debug.MemoryInfo[] mis=am.getProcessMemoryInfo(pids);
        for (Debug.MemoryInfo mi:mis){
            Log.d("rza","TPSS="+mi.getTotalPss());
            mem=mem+mi.getTotalPss();
        }
        return mem;
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