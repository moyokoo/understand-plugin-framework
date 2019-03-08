package com.weishu.upf.dynamic_proxy_hook.app2.dynamic_proxy;

public class HookActivity {
    static A a;

    static A getA() {
        return a;
    }

    void doSome(String name) {
        a.print(name);
    }
}
