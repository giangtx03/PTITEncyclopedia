package com.project.ptittoanthu.users.exception;

public class PasswordNotMatches extends RuntimeException {
    public PasswordNotMatches(String message) {
        super(message);
    }
}
