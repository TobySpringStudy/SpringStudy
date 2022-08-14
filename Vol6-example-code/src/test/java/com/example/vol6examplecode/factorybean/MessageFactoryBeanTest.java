package com.example.vol6examplecode.factorybean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ContextConfiguration
class MessageFactoryBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void getMessageFactoryBean() {
        Object message = context.getBean("message");

        assertAll(
                () -> assertThat(message).isInstanceOf(Message.class),
                () -> assertThat(((Message) message).getText()).isEqualTo("Factory Bean")
        );
    }

}