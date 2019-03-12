package com.weishu.upf.dynamic_proxy_hook.app2.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyCallback implements Handler.Callback {
    Handler handler;

    public MyCallback(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.e("666", "what is " + msg.what + " toString:" + msg.toString());
        handler.handleMessage(msg);
        return true;
    }
}
