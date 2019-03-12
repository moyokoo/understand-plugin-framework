package com.weishu.upf.dynamic_proxy_hook.app2.static_proxy;

public class ProxyEat implements Eat {

    Eat eatMan = new EatMan();

    @Override
    public void eatOne() {
        eatMan.eatOne();
    }

    @Override
    public void eatTwo(int num) {
        eatMan.eatTwo(num);
    }
}
