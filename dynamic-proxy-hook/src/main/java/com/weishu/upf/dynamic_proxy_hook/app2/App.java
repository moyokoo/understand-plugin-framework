package com.weishu.upf.dynamic_proxy_hook.app2;

import android.app.Application;
import android.content.Context;

import com.weishu.upf.dynamic_proxy_hook.app2.hook.HookHelper;

public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            HookHelper.hookHnalder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
