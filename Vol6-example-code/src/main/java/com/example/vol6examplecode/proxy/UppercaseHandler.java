package com.example.vol6examplecode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UppercaseHandler implements InvocationHandler {

    Object target;

    // 타깃 오브젝트 위임
    public UppercaseHandler(Object target) {
        this.target = target;
    }

    // 동적 프록시가 클라이언트로부터 받는 모든 요청은 invoke() 메서드로 전달
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Reflection API를 통해 타깃 오브젝트의 메서드 호출
        Object ret = method.invoke(target, args);

        // 부가기능
        if (ret instanceof String) {
            return ((String) ret).toUpperCase();
        }

        // 리턴 타입 & 메서드 이름이 일치하는 경우에만 부가기능 적용
//        if (ret instanceof String && method.getName().startsWith("say")) {
//            return ((String) ret).toUpperCase();
//        }
        return ret;
    }
}
