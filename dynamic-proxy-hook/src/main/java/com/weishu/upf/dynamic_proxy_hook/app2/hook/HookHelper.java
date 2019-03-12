package com.weishu.upf.dynamic_proxy_hook.app2.hook;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.weishu.upf.dynamic_proxy_hook.app2.handler.MyCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author weishu
 * @date 16/1/28
 */
public class HookHelper {

    public static void attachContext() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }

    public static void hooActivity(Activity activity) throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.Activity");

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(activity);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(activity, evilInstrumentation);
    }

    public static void hookActivtyManagerService() throws Exception {
        Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
        //获取activityManager中的IActivityManagerSingleton字段
        Field ac = activityManageClazz.getDeclaredField("IActivityManagerSingleton");
        ac.setAccessible(true);
        Object defaultSingleton = ac.get(null);
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iActivityManager = mInstanceField.get(defaultSingleton);
        Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerClazz}, new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(defaultSingleton, proxy);


//        final String VIBRATOR_SERVICE = "activity";
//        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
//        Method getService = serviceManager.getDeclaredMethod("getService", String.class);
//        IBinder rawBinder = (IBinder) getService.invoke(null, VIBRATOR_SERVICE);
//        IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
//                new Class<?>[]{IBinder.class},
//                new BinderProxyHookHandler(rawBinder));
//        Field cacheField = serviceManager.getDeclaredField("sCache");
//        cacheField.setAccessible(true);
//        Map<String, IBinder> cache = (Map) cacheField.get(null);
//        cache.put(VIBRATOR_SERVICE, hookedBinder);
    }

    public static void hookHnalder() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            Field handlerField = activityThreadClass.getDeclaredField("mH");
            handlerField.setAccessible(true);
            Handler handler = (Handler) handlerField.get(sCurrentActivityThread);
            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            callbackField.set(handler, new MyCallback(handler));
            Log.e("1", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
