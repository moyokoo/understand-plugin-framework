package com.weishu.upf.dynamic_proxy_hook.app2.static_proxy;

public class EatMan implements Eat {
    @Override
    public void eatOne() {

    }

    @Override
    public void eatTwo(int num) {
        System.out.print("num is :" + num);
    }
}
