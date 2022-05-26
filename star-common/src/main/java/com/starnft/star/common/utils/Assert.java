package com.starnft.star.common.utils;

import com.starnft.star.common.exception.StarException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Harlan
 * @date 2022/05/26 20:44
 */
public class Assert {

    public static void isTrue(boolean expression, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (!expression) {
            throw starExceptionSupplier.get();
        }
    }

    public static void isFalse(boolean expression, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (expression) {
            throw starExceptionSupplier.get();
        }
    }

    public static <T> T notNull(T object, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (Objects.isNull(object)) {
            throw starExceptionSupplier.get();
        }
        return object;
    }

    public static void isNull(Object object, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (!Objects.isNull(object)) {
            throw starExceptionSupplier.get();
        }
    }

    public static <T extends CharSequence> T notEmpty(T text, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (StringUtils.isEmpty(text)) {
            throw starExceptionSupplier.get();
        }
        return text;
    }

    public static <T extends CharSequence> T notBlank(T text, @NonNull Supplier<StarException> starExceptionSupplier) {
        if (StringUtils.isBlank(text)) {
            throw starExceptionSupplier.get();
        }
        return text;
    }

}
