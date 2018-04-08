package com.zhiai.gttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhiai.gttest.utils.MemUtils;

public class MainActivity extends AppCompatActivity {

    Button bt;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt_getMeminfo);
        et = (EditText) findViewById(R.id.et_pkgName);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getMemInfo();
//                getPSS(MainActivity.this,23432);
                MemUtils.getProcessPID(MainActivity.this,et.getText().toString());
//                testss();
            }
        });
    }
}
