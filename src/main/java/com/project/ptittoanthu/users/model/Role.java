package com.project.ptittoanthu.users.model;

public enum Role {
    ADMIN(0),
    STUDENT(1),
    TEACHER(2);

    public final int value;

    Role(int value) {
        this.value = value;
    }

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
