package com.yorhp.screenrecord;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yorhp.recordlibrary.OnScreenShotListener;
import com.yorhp.recordlibrary.ScreenRecordUtil;
import com.yorhp.recordlibrary.ScreenShotUtil;
import com.yorhp.recordlibrary.ScreenUtil;
import com.yorhp.screenrecord.app.MyApplication;

import java.io.File;

import permison.FloatWindowManager;
import permison.PermissonUtil;

public class MainActivity extends AppCompatActivity {

    ImageView iv_pre;

    private int screenRecordBitrate = 32 * 1024 * 1024;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_pre = findViewById(R.id.iv_pre);

        PermissonUtil.checkPermission(this, null, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ScreenUtil.getScreenSize(this);


        ScreenRecordUtil.getInstance().init(ScreenUtil.SCREEN_WIDTH, ScreenUtil.SCREEN_HEIGHT, screenRecordBitrate);
        String savePath = MyApplication.baseDir + System.currentTimeMillis() + ".mp4";
        ScreenRecordUtil.getInstance().start(MainActivity.this, savePath);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                    ScreenRecordUtil.getInstance().destroy();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        /*//申请悬浮窗权限
        FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this);
        ScreenShotUtil.getInstance().screenShot(this, new OnScreenShotListener() {
            @Override
            public void screenShot() {
                iv_pre.setImageBitmap(ScreenShotUtil.getInstance().getScreenShot());

            }
        });

        iv_pre.setOnClickListener(new View.OnClickListener() {//可以截屏
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, MyService.class));
                moveTaskToBack(true);
            }
        });*/


    }
}
