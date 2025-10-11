package com.project.ptittoanthu.common.enums;

public enum KeyTypeEnum {
    ACTIVE("active", 2),
    FORGOT_PASSWORD("forgot_password", 2),
    BLACKLIST_TOKEN("blacklist", 60 * 24 * 7),
    REQUEST_LIMIT("request_limit", 10),
    LOGIN_FAIL("login_fail", 5),
    REGISTER_LIMIT("register_limit", 60);

    public final long time;
    public final String value;

    KeyTypeEnum(String value, long time) {
        this.value = value;
        this.time = time;
    }
}
