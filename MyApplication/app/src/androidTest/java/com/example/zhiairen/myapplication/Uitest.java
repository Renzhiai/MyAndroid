package com.example.zhiairen.myapplication;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.InstrumentationTestCase;

import java.io.File;

/**
 * Created by zhiai.ren on 2016/8/19.
 */
public class Uitest extends UiAutomatorTestCase {
    public void testDemo() throws UiObjectNotFoundException,Exception{
        UiDevice d=getUiDevice();
        d.pressHome();
        File f=new File("d:/aa.xml");
        d.dumpWindowHierarchy(f);
    }
}
