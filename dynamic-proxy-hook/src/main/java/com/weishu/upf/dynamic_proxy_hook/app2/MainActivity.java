package com.weishu.upf.dynamic_proxy_hook.app2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.view.View;
import android.widget.Button;


import com.weishu.upf.dynamic_proxy_hook.app2.hook.HookHelper;

import java.lang.reflect.Method;

/**
 * @author weishu
 * @date 16/1/28
 */
public class MainActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 16/1/28 支持Activity直接跳转请在这里Hook
        // 家庭作业,留给读者完成.
        try {
            // 在这里进行Hook
            HookHelper.hooActivity(this);
            HookHelper.hookActivtyManagerService();

        } catch (Exception e) {
            e.printStackTrace();
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };


        Button tv = new Button(this);
        tv.setText("测试界面");

        setContentView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(1);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            // 在这里进行Hook
            HookHelper.attachContext();
            HookHelper.hookHnalder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
