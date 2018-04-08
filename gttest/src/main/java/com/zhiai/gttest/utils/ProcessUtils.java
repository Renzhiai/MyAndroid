package com.zhiai.gttest.utils;

/**
 * Created by zhiai on 2018/4/8.
 */

public class ProcessUtils {
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
}
