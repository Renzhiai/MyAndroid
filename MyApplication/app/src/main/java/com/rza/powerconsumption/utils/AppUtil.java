package com.rza.powerconsumption.utils;

import android.view.View;
import android.widget.EditText;

public class AppUtil {

    //判断某个字符串是否是数字
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    //根据输入内容获取pid
    public static int[] getPid(EditText et){
        //获得输入框的内容
        String pidStr=et.getText().toString();
        //如果输入框内容没有空格，而且可以转换成数字
        if (!pidStr.contains(" ") && isNumeric(pidStr)){
            int[] pids=new int[1];
            pids[0]=Integer.parseInt(pidStr);
            return pids;
        }
        //如果输入框内容有空格
        else if(pidStr.contains(" ")){
            //用空格分割输入框的内容
            String[] pidStrArr=pidStr.split(" ");
            //准备一个数组，转换成int类型
            int[] pids = new int[pidStrArr.length];
            //遍历分割的数组
            for (int i = 0; i <pidStrArr.length; i++){
                //如果数组元素可以转换成数字，就把它转换成数字类型
                if (isNumeric(pidStrArr[i])) {
                    pids[i]=Integer.parseInt(pidStrArr[i]);
                }
            }
            return pids;
        }
        else{
            return null;
        }
    }
}
