package com.example.vol6examplecode.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloTest {

    @Test
    void helloTargetTest() {
        Hello hello = new HelloTarget();

        assertAll(
                () -> assertThat(hello.sayHello("JuHyun")).isEqualTo("Hello JuHyun"),
                () -> assertThat(hello.sayHi("JuHyun")).isEqualTo("Hi JuHyun"),
                () -> assertThat(hello.sayThankYou("JuHyun")).isEqualTo("Thank You JuHyun")
        );
    }

    @Test
    void helloUppercaseProxyTest() {
        Hello proxied = new HelloUppercaseProxy(new HelloTarget());

        assertAll(
                () -> assertThat(proxied.sayHello("JuHyun")).isEqualTo("HELLO JUHYUN"),
                () -> assertThat(proxied.sayHi("JuHyun")).isEqualTo("HI JUHYUN"),
                () -> assertThat(proxied.sayThankYou("JuHyun")).isEqualTo("THANK YOU JUHYUN")
        );
    }

    /* JDK Dynamic Proxy */
    @Test
    void dynamicProxyTest() {
        Hello proxied = (Hello) Proxy.newProxyInstance(
                UppercaseHandler.class.getClassLoader(), // 클래스 로딩에 사용할 클래스 로더
                new Class[]{Hello.class}, // 동적 프록시가 구현할 인터페이스
                new UppercaseHandler(new HelloTarget()) // 부가기능 & 위임 코드를 담은 InvocationHandler
        );

        assertAll(
                () -> assertThat(proxied.sayHello("JuHyun")).isEqualTo("HELLO JUHYUN"),
                () -> assertThat(proxied.sayHi("JuHyun")).isEqualTo("HI JUHYUN")
        );
    }

    /* ProxyFactoryBean */
    @Test
    void proxyFactoryBeanTest() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget()); // 타깃 설정
        proxyFactoryBean.addAdvice(new UppercaseAdvice()); // 부가기능을 담은 어드바이스 추가(여러개 가능)

        Hello proxied = (Hello) proxyFactoryBean.getObject();

        assertAll(
                () -> assertThat(proxied.sayHello("JuHyun")).isEqualTo("HELLO JUHYUN"),
                () -> assertThat(proxied.sayHi("JuHyun")).isEqualTo("HI JUHYUN"),
                () -> assertThat(proxied.sayThankYou("JuHyun")).isEqualTo("THANK YOU JUHYUN")
        );
    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();

            // InvocationHandler와는 달리 메서드 실행 시 타깃 오브젝트 전달할 필요 X
            return ret.toUpperCase(); // 부가기능 적용
        }
    }

    @Test
    void pointCutAdvisorTest() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        /* 포인트컷 - 메서드 이름을 비교해서 부가기능이 적용될 대상(메서드)를 선정 */
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // 포인트컷 + 어드바이스를 Advisor로 묶어서 한 번에 추가
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxied = (Hello) proxyFactoryBean.getObject();

        assertAll(
                () -> assertThat(proxied.sayHello("JuHyun")).isEqualTo("HELLO JUHYUN"),
                () -> assertThat(proxied.sayHi("JuHyun")).isEqualTo("HI JUHYUN"),
                () -> assertThat(proxied.sayThankYou("JuHyun")).isNotEqualTo("THANK YOU JUHYUN") // 부가기능 적용 X
        );
    }

}
