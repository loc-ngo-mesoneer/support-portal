package io.locngo.support.portal.validation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiValidator {
    public static void requireNonNull(final Object value, final String fieldName) {
        
        if (Objects.isNull(value)) {
            final String errorMessage = String.format(
                "[%s] must be not null !",
                fieldName
            );
            final IllegalArgumentException exception = new IllegalArgumentException(errorMessage);
            log.error(errorMessage, exception);
            throw exception;
        }
    }

    public static void requireNotBlank(final String value, final String fieldName) {
        if (StringUtils.isBlank(value)) {
            final String errorMessage = String.format(
                "[%s] must be not blank !",
                fieldName
            );
            final IllegalArgumentException exception = new IllegalArgumentException(errorMessage);
            log.error(errorMessage, exception);
            throw exception;
        }
    }
}
