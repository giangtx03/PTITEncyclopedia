package com.project.ptittoanthu.common.enums;

public enum NotificationTypeEnum {
    BOOKING(0), FAVORITE(1), REVIEW(2), PAYMENT(3);

    public final int value;

    NotificationTypeEnum(int value) {
        this.value = value;
    }

    public static NotificationTypeEnum fromValue(int value) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.value == value) {
                return notificationTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Notification Type Enum value: " + value);
    }
}
