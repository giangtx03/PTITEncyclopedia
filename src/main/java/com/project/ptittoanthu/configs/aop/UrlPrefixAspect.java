package com.project.ptittoanthu.configs.aop;

import com.project.ptittoanthu.common.base.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Aspect
@Component
@RequiredArgsConstructor // Sử dụng constructor injection, tốt hơn @Autowired
public class UrlPrefixAspect {

    // Tiêm Environment để có thể đọc bất kỳ thuộc tính nào một cách linh hoạt
    private final Environment environment;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object wrapUrlInResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result == null) {
            return null;
        }

        // Tạo map visited mới cho mỗi request để tránh xung đột thread
        Map<Object, Boolean> visited = new IdentityHashMap<>();

        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            if (body instanceof ResponseDto<?> responseDto) {
                processFields(responseDto.getData(), visited);
            } else {
                processFields(body, visited);
            }
            return responseEntity;
        }

        processFields(result, visited);
        return result;
    }

    private void processFields(Object obj, Map<Object, Boolean> visited) {
        if (obj == null || visited.containsKey(obj)) {
            return;
        }

        visited.put(obj, true);

        if (obj instanceof Collection<?> collection) {
            collection.forEach(item -> processFields(item, visited));
        } else if (obj.getClass().isArray() && !obj.getClass().getComponentType().isPrimitive()) {
            for (Object item : (Object[]) obj) {
                processFields(item, visited);
            }
        } else if (obj instanceof Map<?, ?> map) {
            map.values().forEach(value -> processFields(value, visited));
        } else if (!isJavaInternalClass(obj.getClass())) {
            updatePrefixedFields(obj, visited);
        }
    }

    private void updatePrefixedFields(Object obj, Map<Object, Boolean> visited) {
        Class<?> clazz = obj.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    // Thay vì kiểm tra @ImageField, ta kiểm tra @UrlPrefix
                    if (field.isAnnotationPresent(UrlPrefix.class)) {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        if (value == null) continue;

                        // Lấy annotation và đọc giá trị property
                        UrlPrefix annotation = field.getAnnotation(UrlPrefix.class);
                        String propertyKey = annotation.property();
                        String baseUrl = environment.getProperty(propertyKey);

                        if (baseUrl == null || baseUrl.isBlank()) {
                            // Bỏ qua nếu không tìm thấy base URL trong config
                            continue;
                        }

                        // Logic xử lý String và List<String> giữ nguyên
                        if (value instanceof String path) {
                            if (!path.startsWith("http")) {
                                field.set(obj, baseUrl + path.replace("\\", "/"));
                            }
                        } else if (value instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof String) {
                            List<String> updatedList = ((List<String>) list).stream()
                                    .map(item -> item.startsWith("http") ? item : baseUrl + item.replace("\\", "/"))
                                    .toList();
                            field.set(obj, updatedList);
                        }
                    } else {
                        // Nếu không phải @UrlPrefix, tiếp tục đệ quy như bình thường
                        field.setAccessible(true);
                        processFields(field.get(obj), visited);
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private boolean isJavaInternalClass(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz.getName().startsWith("java.")
                || clazz.getName().startsWith("jakarta.")
                || clazz.isEnum();
    }
}