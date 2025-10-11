package com.project.ptittoanthu.authentication.exception;

public class UserDoesNotHavePermission extends RuntimeException {
    public UserDoesNotHavePermission(String message) {
        super(message);
    }
}
