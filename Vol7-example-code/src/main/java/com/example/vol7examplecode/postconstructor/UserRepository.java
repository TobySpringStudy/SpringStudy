package com.example.vol7examplecode.postconstructor;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Map<String, String> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUserId(), user.getPassword());
    }

}
