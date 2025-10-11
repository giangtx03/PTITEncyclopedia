package com.project.ptittoanthu.common.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class OtpGenerator {

    public String createOtp() {
        SecureRandom random = new SecureRandom();
        StringBuffer otp = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        System.out.println(otp);
        return otp.toString();
    }
}
