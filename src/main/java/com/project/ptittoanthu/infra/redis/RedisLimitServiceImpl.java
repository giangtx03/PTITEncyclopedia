package com.project.ptittoanthu.infra.redis;

import com.project.ptittoanthu.common.base.enums.KeyTypeEnum;
import com.project.ptittoanthu.common.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service("redis")
//@Primary
//@RequiredArgsConstructor
public class RedisLimitServiceImpl implements LimitService {
//    private final RedisTemplate<String, Object> redisTemplate;
//    @Value("${login.max.attempts}")
//    private int loginMaxAttempts;
//    @Value("${register.max.attempts}")
//    private int registerMaxAttempts;
//    @Value("${request.max.attempts}")
//    private int requestMaxAttempts;
//
//    @Override
//    public boolean isLoginBlocked(String email) {
//        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Object attemptsObj = ops.get(key);
//        if (attemptsObj != null) {
//            int attempts = (int) attemptsObj;
//            return attempts >= loginMaxAttempts;
//        }
//        return false;
//    }
//
//    @Override
//    public void increaseLoginAttempts(String email) {
//        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Integer attempts = (Integer) ops.get(key);
//        if (attempts == null) {
//            ops.set(key, 1, KeyTypeEnum.LOGIN_FAIL.time, TimeUnit.MINUTES);
//        } else {
//            ops.set(key, attempts + 1, KeyTypeEnum.LOGIN_FAIL.time, TimeUnit.MINUTES);
//        }
//    }
//
//    @Override
//    public void resetLoginAttempts(String email) {
//        String key = RedisUtils.createKey(KeyTypeEnum.LOGIN_FAIL.value, email);
//        redisTemplate.delete(key);
//    }
//
//    @Override
//    public boolean isRegisterBlocked(String ip) {
//        String key = RedisUtils.createKey(KeyTypeEnum.REGISTER_LIMIT.value, ip);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Object attemptsObj = ops.get(key);
//        if (attemptsObj != null) {
//            int attempts = (int) attemptsObj;
//            return attempts >= registerMaxAttempts;
//        }
//        return false;
//    }
//
//    @Override
//    public void increaseRegisterAttempts(String ip) {
//        String key = RedisUtils.createKey(KeyTypeEnum.REGISTER_LIMIT.value, ip);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Integer attempts = (Integer) ops.get(key);
//        if (attempts == null) {
//            ops.set(key, 1, KeyTypeEnum.REGISTER_LIMIT.time, TimeUnit.MINUTES);
//        } else {
//            ops.set(key, attempts + 1, KeyTypeEnum.REGISTER_LIMIT.time, TimeUnit.MINUTES);
//        }
//    }
//
//    @Override
//    public boolean isRequestBlocked(String ip) {
//        String key = RedisUtils.createKey(KeyTypeEnum.REQUEST_LIMIT.value, ip);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Object attemptsObj = ops.get(key);
//        if (attemptsObj != null) {
//            int attempts = (int) attemptsObj;
//            return attempts >= requestMaxAttempts;
//        }
//        return false;
//    }
//
//    @Override
//    public void increaseRequestAttempts(String ip) {
//        String key = RedisUtils.createKey(KeyTypeEnum.REQUEST_LIMIT.value, ip);
//        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
//
//        Integer attempts = (Integer) ops.get(key);
//        if (attempts == null) {
//            ops.set(key, 1, KeyTypeEnum.REQUEST_LIMIT.time, TimeUnit.MINUTES);
//        } else {
//            ops.set(key, attempts + 1, KeyTypeEnum.REQUEST_LIMIT.time, TimeUnit.MINUTES);
//        }
//    }
}
