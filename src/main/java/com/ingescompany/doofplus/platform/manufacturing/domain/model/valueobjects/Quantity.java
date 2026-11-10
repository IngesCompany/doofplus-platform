    package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

    import java.math.BigDecimal;
    import java.util.regex.Pattern;

    /**
     * Value object representing a Quantity.
     *
     * @param value the quantity value
     * @param unit  the unit of measurement
     */
    public record Quantity(BigDecimal value, String unit) {
        private static final int MAX_LENGTH = 10;
        private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");

        public Quantity {
            if (value == null) {
                throw new IllegalArgumentException("manufacturing.error.quantity.value.notNull");
            }
            if (unit == null || unit.isBlank()) {
                throw new IllegalArgumentException("manufacturing.error.quantity.unit.notBlank");
            }
            if (unit.length() > MAX_LENGTH){
                throw new IllegalArgumentException("manufacturing.error.quantity.unit.size");
            }
            if (value.signum() < 0){
                throw new IllegalArgumentException("manufacturing.error.quantity.value.negative");
            }
            if (!ALLOWED_PATTERN.matcher(unit).matches()){
                throw new IllegalArgumentException("manufacturing.error.quantity.unit.pattern");
            }
        }
    }
