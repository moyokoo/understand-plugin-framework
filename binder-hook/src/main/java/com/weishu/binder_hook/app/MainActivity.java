package com.weishu.binder_hook.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.EditText;

/**
 * @author weishu
 * @date 16/2/15
 */
public class MainActivity extends Activity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //由于有缓存 因此不能同一个时间测试前后状态
//        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        boolean before = vibrator.hasVibrator();
        try {
            BinderHookHelper.hookClipboardService();
            BinderHookHelper.hooVibratorService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EditText editText = new EditText(this);
        //该服务返回手机是否支持震动,为true,这里hook修改为false
//        Vibrator vibrator2 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        editText.setText("hasVibrator:  hook after:" + vibrator2.hasVibrator());
        setContentView(editText);
    }
}
