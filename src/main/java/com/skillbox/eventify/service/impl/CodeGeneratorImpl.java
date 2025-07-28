package com.skillbox.eventify.service.impl;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;
import com.skillbox.eventify.service.CodeGenerator;

@Component
public class CodeGeneratorImpl implements CodeGenerator {
    private static final String CHARACTERS = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private final SecureRandom random = new SecureRandom();
    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
