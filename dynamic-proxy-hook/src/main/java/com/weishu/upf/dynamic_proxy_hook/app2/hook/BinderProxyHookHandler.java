package com.weishu.upf.dynamic_proxy_hook.app2.hook;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BinderProxyHookHandler implements InvocationHandler {

    private static final String TAG = "BinderProxyHookHandler";
    IBinder base;

    Class<?> stub;

    Class<?> iinterface;

    public BinderProxyHookHandler(IBinder base) {
        this.base = base;
        try {
            this.stub = Class.forName("android.app.IActivityManager$Stub");
            this.iinterface = Class.forName("android.app.IActivityManager");
            Log.e(TAG, "BinderProxyHookHandler  ");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "BinderProxyHookHandler error " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d(TAG, "hook queryLocalInterface");
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[] { this.iinterface },
                    new BinderHookHandler(base, stub));
        }
        return method.invoke(base, args);
    }
}
