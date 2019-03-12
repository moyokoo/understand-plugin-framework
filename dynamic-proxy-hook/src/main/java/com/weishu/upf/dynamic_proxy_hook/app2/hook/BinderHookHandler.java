package com.weishu.upf.dynamic_proxy_hook.app2.hook;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author weishu
 * @date 16/2/16
 */
public class BinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    // 原始的Service对象 (IInterface)
    Object base;

    public BinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!" + e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            Log.e(TAG, "hook startActivity queryLocalInterface");
        }

        return method.invoke(base, args);
    }
}
