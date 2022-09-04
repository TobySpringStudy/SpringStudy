package com.example.vol7examplecode.postconstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /* 생성자에서는 주입이 안되어서 에러 발생 */
//    public UserService() {
//        User admin = new User("admin", "password1");
//        userRepository.save(admin);
//    }

    @PostConstruct
    private void init() {
        User admin = new User("admin", "password1");
        User normalUser = new User("user", "password2");
        userRepository.save(admin);
        userRepository.save(normalUser);
    }

}
