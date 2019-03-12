package com.weishu.upf.dynamic_proxy_hook.app2.dynamic_proxy;

import com.weishu.upf.dynamic_proxy_hook.app2.Shopping;
import com.weishu.upf.dynamic_proxy_hook.app2.ShoppingImpl;
import com.weishu.upf.dynamic_proxy_hook.app2.static_proxy.Eat;
import com.weishu.upf.dynamic_proxy_hook.app2.static_proxy.EatMan;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author weishu
 * @date 16/1/28
 */
public class TestDynamic {

    public static void main(String[] args) {
//        Shopping women = new ShoppingImpl();
//
//        // 正常购物
//        System.out.println(Arrays.toString(women.doShopping(100)));
//
//        // 招代理
//        women = (Shopping) Proxy.newProxyInstance(Shopping.class.getClassLoader(),
//                women.getClass().getInterfaces(), new ShoppingHandler(women));
//
//        System.out.println(Arrays.toString(women.doShopping(100)));


        Eat eat = new EatMan();

        eat = (Eat) Proxy.newProxyInstance(Eat.class.getClassLoader(), eat.getClass().getInterfaces(), new EatHandler(eat));
        eat.eatOne();
        eat.eatTwo(1);
    }

    static class EatHandler implements InvocationHandler {
        Object base;

        EatHandler(Object o) {
            this.base = o;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("eatTwo")) {
                return method.invoke(base, 666);
            }
            return method.invoke(base, args);
        }
    }


}
