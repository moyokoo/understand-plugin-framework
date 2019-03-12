package com.weishu.upf.dynamic_proxy_hook.app2.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerProxy implements InvocationHandler {

    Object iActivityManager;

    IActivityManagerProxy(Object o) {
        this.iActivityManager = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            Log.e("222", "33333333");
        }
        return method.invoke(iActivityManager, args);
    }
}
