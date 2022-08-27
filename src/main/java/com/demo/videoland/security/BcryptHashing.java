package com.demo.videoland.security;

import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;

public class BcryptHashing {
    public static String hashBcrypt(String text) {
        return hashpw(text, "$2a$10$llw0G6IyibUob8h5XRt9xuRczaGdCm/AiV6SSjf5v78XS824EGbh.");
    }
}
