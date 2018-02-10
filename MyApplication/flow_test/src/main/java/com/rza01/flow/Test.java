package com.rza01.flow;

import java.io.PrintStream;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Test {
        public static void main(String[]agrs)
        {
            try {
                System.out.println("HelloWorld!");
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec("adb shell");
                System.out.print(proc);
            }catch (Exception e){
                System.err.println(e);
            }
        }
}
