package com.example.test02;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		//注册广播接收者
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		//创建广播接收者对象
		BatteryReceiver batteryReceiver = new BatteryReceiver();
		//注册receiver
		registerReceiver(batteryReceiver, intentFilter);
//		unregisterReceiver(batteryReceiver);
    }
    
    private TextView tv;
    private TextView tv2;
    
	/**
	 * 广播接收者
	 */
	class BatteryReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//判断它是否是为电量变化的Broadcast Action
			if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
				//获取当前电量
				int level = intent.getIntExtra("level", 0);
				//电量的总刻度
				int scale = intent.getIntExtra("scale", 100);
				//找到控件
				tv=(TextView)findViewById(R.id.tv);
				tv2=(TextView)findViewById(R.id.tv2);
				//设置电量
				tv.setText("电池电量为"+((level*100)/scale)+"%");
				//获取时间
				Date now=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				String time=sdf.format(now);
				//设置时间
				tv2.setText("当前时间:"+time);
				//保存到/mnt/sdcard/info.txt
				String filepath =Environment.getExternalStorageDirectory().getPath();
		    	File file=new File(filepath, "/info.txt");
		    	String string=tv.getText().toString()+"\n"+tv2.getText().toString()+"\n\n";
		    	try{
		    		//追加写入
					FileOutputStream fos=new FileOutputStream(file,true);
					fos.write((string).getBytes());
					fos.close();
		    	}catch(Exception e){
		    	  e.printStackTrace();
				}
			}
		}
	}
}
