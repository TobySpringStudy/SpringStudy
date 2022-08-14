package com.example.vol6examplecode.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {

    private String text;

    public MessageFactoryBean(String text) {
        this.text = text;
    }

    // 실제 빈으로 사용될 오브젝트 생성 -> Message
    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(this.text);

        // FactoryBean을 사용하여 스프링 빈으로 등록할 수 없는 동적 프록시 오브젝트를 빈으로 생성
        //return Proxy.newProxyInstance(...)
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
