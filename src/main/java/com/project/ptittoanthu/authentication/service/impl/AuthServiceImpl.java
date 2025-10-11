package com.project.ptittoanthu.authentication.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.ptittoanthu.authentication.dto.TokenDTO;
import com.project.ptittoanthu.authentication.dto.request.LoginRequest;
import com.project.ptittoanthu.authentication.dto.request.RegisterRequest;
import com.project.ptittoanthu.authentication.dto.request.SetPasswordRequest;
import com.project.ptittoanthu.authentication.dto.request.VerifyOtpRequest;
import com.project.ptittoanthu.authentication.dto.response.LoginResponse;
import com.project.ptittoanthu.authentication.dto.response.UserResponse;
import com.project.ptittoanthu.authentication.exception.EmailExistsException;
import com.project.ptittoanthu.authentication.exception.UserIsAvtiveException;
import com.project.ptittoanthu.authentication.exception.UserNotFoundException;
import com.project.ptittoanthu.authentication.mapper.UserMapper;
import com.project.ptittoanthu.authentication.model.User;
import com.project.ptittoanthu.authentication.repository.UserRepository;
import com.project.ptittoanthu.authentication.service.AuthService;
import com.project.ptittoanthu.common.constant.EmailConstant;
import com.project.ptittoanthu.common.enums.KeyTypeEnum;
import com.project.ptittoanthu.common.exception.OtpInvalidException;
import com.project.ptittoanthu.common.util.OtpGenerator;
import com.project.ptittoanthu.common.util.RedisUtils;
import com.project.ptittoanthu.configs.security.JwtProvider;
import com.project.ptittoanthu.infra.mail.MailSender;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepo;
    final UserMapper userMapper;
    final JwtProvider jwtProvider;
    final PasswordEncoder passwordEncoder;
    final RedisTemplate<String, Object> redisTemplate;
    final MailSender mailSender;
    final AuthenticationManager authenticationManager;

    @Value("${jwt.accessKey}")
    private String accessKey;
    @Value("${jwt.refreshKey}")
    private String refreshKey;
    @Value("${jwt.resetPasswordKey}")
    private String resetPasswordKey;
    @Value("${jwt.expiration.time.resetPassword}")
    private long timeResetPassword;
    @Value("${jwt.expiration.time.access}")
    private long timeAccess;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        User userModel = (User) authentication.getPrincipal();

        TokenDTO tokenDto = jwtProvider.generateToken(userModel);

        UserResponse userResponse = userMapper.toResponse(userModel);
        return LoginResponse.builder()
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .user(userResponse)
                .build();
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) throws MessagingException {
        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistsException("Email has existed");
        }

        User user = userMapper.toUser(registerRequest, passwordEncoder);
        User saved = userRepo.save(user);
        UserResponse userResponse = userMapper.toResponse(saved);

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.ACTIVE, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Xác thực đăng ký tài khoản",
                EmailConstant.OTP_MAIL, Map.of("name", user.getUsername(), "otp", otp));
        return userResponse;
    }

    @Transactional

    public void activateAccount(VerifyOtpRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.ACTIVE.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));
        user.setActive(true);
        userRepo.save(user);

        redisTemplate.delete(key);
    }

    @Override
    public void resendEmailActive(String email) throws MessagingException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        if (user.isActive()) {
            throw new UserIsAvtiveException(email);
        }

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.ACTIVE, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Xác thực tài khoản",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", user.getUsername(),
                        "otp", otp));
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        String otp = OtpGenerator.createOtp();
        saveOtp(KeyTypeEnum.FORGOT_PASSWORD, user.getEmail(), otp);
        mailSender.sendEmailAsync(user.getEmail(), "Đặt lại mật khẩu",
                EmailConstant.OTP_MAIL, Map.of(
                        "name", user.getUsername(),
                        "otp", otp));
    }

    @Override
    public String verifyOtpForgotPassword(VerifyOtpRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        String key = RedisUtils.createKey(KeyTypeEnum.FORGOT_PASSWORD.value, request.getEmail());
        if (!validateOtp(key, request.getOtp())) {
            throw new OtpInvalidException("Otp invalid");
        }
        redisTemplate.delete(key);
        return jwtProvider.generateToken(user, resetPasswordKey, timeResetPassword);
    }

    @Transactional
    @Override
    public void setPassword(SetPasswordRequest request) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, request.getToken());
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid token set password");
        }
        DecodedJWT decodeToken = jwtProvider.decodeToken(request.getToken(), resetPasswordKey);
        String email = decodeToken.getSubject();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        redisTemplate.opsForValue().set(key, "invalid",
                KeyTypeEnum.BLACKLIST_TOKEN.time, TimeUnit.MINUTES);
    }

    public String refreshToken(String refreshToken) {
        log.error(refreshToken);
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, refreshToken);
        if (redisTemplate.hasKey(key)) {
            throw new JWTVerificationException("Invalid refresh token");
        }

        DecodedJWT decodeToken = jwtProvider.decodeToken(refreshToken, refreshKey);
        String email = decodeToken.getSubject();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        return jwtProvider.generateToken(user, accessKey, timeAccess);
    }

    @Override
    public void logout(String refreshToken) {
        String key = RedisUtils.createKey(KeyTypeEnum.BLACKLIST_TOKEN.value, refreshToken);
        redisTemplate.opsForValue().set(key, "invalid",
                KeyTypeEnum.BLACKLIST_TOKEN.time, TimeUnit.MINUTES);

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private void saveOtp(KeyTypeEnum keyTypeEnum, String subKey, String otp) {
        String key = RedisUtils.createKey(keyTypeEnum.value, subKey);
        redisTemplate.opsForValue().set(key, otp, keyTypeEnum.time, TimeUnit.MINUTES);
        log.info("OTP của user {} : {}", subKey,
                Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
    }

    private boolean validateOtp(String key, String otp) {
        Object storedOtp = redisTemplate.opsForValue().get(key);
        return storedOtp != null && otp.equals(storedOtp.toString());
    }
}
