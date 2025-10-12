package com.project.ptittoanthu.configs.aop;

import com.project.ptittoanthu.common.base.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ImageFieldAspect {
    @Value("${images.base-url}")
    private String imageBaseUrl;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object wrapImageUrlInResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();

            if (body instanceof ResponseDto<?> responseDto) {
                Object data = responseDto.getData();
                processImageFields(data, new IdentityHashMap<>());
            }

            return responseEntity;
        }

        processImageFields(result, new IdentityHashMap<>());
        return result;
    }


    private void processImageFields(Object obj, Map<Object, Boolean> visited) {
        if (obj == null || visited.containsKey(obj)) return;

        visited.put(obj, true);

        if (obj instanceof Collection<?> collection) {
            for (Object item : collection) processImageFields(item, visited);
        } else if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().isPrimitive()) {
                for (Object item : (Object[]) obj) {
                    processImageFields(item, visited);
                }
            }
        } else if (obj instanceof Map<?, ?> map) {
            for (Object value : map.values()) processImageFields(value, visited);
        } else if (!isJavaInternalClass(obj.getClass())) {
            updateImageFields(obj, visited);
        }
    }


    private void updateImageFields(Object obj, Map<Object, Boolean> visited) {
        Class<?> clazz = obj.getClass();

        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null) continue;

                    if (field.isAnnotationPresent(ImageField.class)) {
                        if (value instanceof String path) {
                            if (!path.startsWith("http")) {
                                String fixedPath = imageBaseUrl + path.replace("\\", "/");
                                field.set(obj, fixedPath);
                            }
                        } else if (value instanceof List<?> list) {
                            if (!list.isEmpty() && list.get(0) instanceof String) {
                                List<String> updatedList = list.stream()
                                        .map(item -> {
                                            String str = (String) item;
                                            return str.startsWith("http") ? str : imageBaseUrl + str.replace("\\", "/");
                                        })
                                        .toList();
                                field.set(obj, updatedList);
                            }
                        }
                    } else {
                        // Nếu không phải @ImageField thì vẫn tiếp tục đệ quy
                        processImageFields(value, visited);
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
