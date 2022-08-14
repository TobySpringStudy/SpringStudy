package com.example.vol6examplecode.proxy;

public class HelloUppercaseProxy implements Hello {

    // 위임할 타깃 오브젝트
    Hello target;

    public HelloUppercaseProxy(Hello target) {
        this.target = target;
    }

    @Override
    public String sayHello(String name) {
//        System.out.println("Logging Start");
//        target.sayHello(name);
//        System.out.println("Logging End");
        return target.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return target.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return target.sayThankYou(name).toUpperCase();
    }
}
