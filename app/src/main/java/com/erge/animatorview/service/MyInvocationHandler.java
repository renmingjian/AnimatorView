package com.erge.animatorview.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mj on 2020/10/2 20:53
 */
class MyInvocationHandler implements InvocationHandler {

    private Object obj;

    public MyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(obj, args);
        return null;
    }
}
