package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Reads the enum and date values the Web App sends. Enums accept any case and hyphens
 * ({@code in-progress} is {@code IN_PROGRESS}); an invalid value answers 400 with the given message key.
 */
public final class CatalogValueParser {

    private CatalogValueParser() {
    }

    public static <E extends Enum<E>> E toEnum(Class<E> type, String value, String messageKey) {
        if (value == null || value.isBlank()) return null;
        try {
            return Enum.valueOf(type, value.trim().toUpperCase(Locale.ROOT).replace('-', '_'));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(messageKey);
        }
    }

    public static LocalDateTime toDateTime(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return OffsetDateTime.parse(value.trim()).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(value.trim());
            } catch (DateTimeParseException ignored) {
                throw new IllegalArgumentException("manufacturing.error.dateTime.invalid");
            }
        }
    }

    public static LocalDate toDate(String value, String messageKey) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(messageKey);
        }
    }

    public static String toText(Object value) {
        return value == null ? null : value.toString();
    }
}
