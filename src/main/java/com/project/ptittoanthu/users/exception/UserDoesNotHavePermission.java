package com.project.ptittoanthu.users.exception;

public class UserDoesNotHavePermission extends RuntimeException {
    public UserDoesNotHavePermission(String message) {
        super(message);
    }
}
