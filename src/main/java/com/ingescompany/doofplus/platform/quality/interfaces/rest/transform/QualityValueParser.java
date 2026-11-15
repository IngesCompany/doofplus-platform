package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;

/**
 * Converts the text values of the Quality resources to their domain types and back.
 * Enums accept kebab-case (in-review), PascalCase (InReview) or UPPER_SNAKE_CASE (IN_REVIEW).
 * Dates travel as yyyy-MM-dd and date-times as ISO 8601 in UTC.
 */
public final class QualityValueParser {

    private QualityValueParser() {
    }

    /**
     * Converts a text to an enum value.
     *
     * @param type       the enum type
     * @param value      the text; null or blank gives null, so the aggregate applies its default
     * @param messageKey the message key thrown when the value is not valid
     * @param <E>        the enum type
     * @return the enum value, or null
     * @throws IllegalArgumentException with the message key when the value is not valid
     */
    public static <E extends Enum<E>> E toEnum(Class<E> type, String value, String messageKey) {
        if (value == null || value.isBlank()) return null;
        String wanted = normalize(value);
        return Arrays.stream(type.getEnumConstants())
                .filter(constant -> normalize(constant.name()).equals(wanted))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(messageKey));
    }

    /**
     * Converts a date in yyyy-MM-dd; a date-time is accepted and keeps its date.
     *
     * @param value      the text, or null
     * @param messageKey the message key thrown when the value is not valid
     * @return the date, or null
     */
    public static LocalDate toDate(String value, String messageKey) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDate.parse(value.trim().length() > 10 ? value.trim().substring(0, 10) : value.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(messageKey);
        }
    }

    /**
     * Converts an ISO 8601 date-time to UTC; without offset it is read as UTC.
     *
     * @param value      the text, or null
     * @param messageKey the message key thrown when the value is not valid
     * @return the date-time in UTC, or null
     */
    public static LocalDateTime toDateTime(String value, String messageKey) {
        if (value == null || value.isBlank()) return null;
        try {
            return OffsetDateTime.parse(value.trim()).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(value.trim());
            } catch (DateTimeParseException ignored) {
                throw new IllegalArgumentException(messageKey);
            }
        }
    }

    /**
     * Gives the text of an enum value, in UPPER_SNAKE_CASE.
     *
     * @param value the enum value, or null
     * @return its name, or null
     */
    public static String fromEnum(Enum<?> value) {
        return value == null ? null : value.name();
    }

    /**
     * Gives the text of a date, in yyyy-MM-dd.
     *
     * @param value the date, or null
     * @return the text, or null
     */
    public static String fromDate(LocalDate value) {
        return value == null ? null : value.toString();
    }

    /**
     * Gives the text of a date-time in UTC, in ISO 8601 with Z.
     *
     * @param value the date-time in UTC, or null
     * @return the text, or null
     */
    public static String fromDateTime(LocalDateTime value) {
        if (value == null) return null;
        Instant instant = value.withNano(0).toInstant(ZoneOffset.UTC);
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }

    private static String normalize(String value) {
        return value.trim().replace("-", "").replace("_", "").replace(" ", "").toUpperCase(Locale.ROOT);
    }
}
