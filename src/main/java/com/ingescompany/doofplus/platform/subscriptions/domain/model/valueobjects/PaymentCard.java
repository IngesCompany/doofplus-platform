package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;


import java.util.regex.Pattern;

/**
 * Card used to pay the subscription. Only the brand, the last four digits and the expiry are kept;
 * the full card number never reaches DoofPlus because Niubiz tokenizes it.
 *
 * @param brand  card brand, for example {@code Visa}
 * @param last4  last four digits of the card
 * @param expiry expiry date in {@code MM/yyyy} format
 */
public record PaymentCard(String brand, String last4, String expiry) {
    private static final Pattern LAST4_PATTERN = Pattern.compile("^\\d{4}$");
    private static final Pattern EXPIRY_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/\\d{4}$");
    private static final String BRAND_NOT_BLANK_MESSAGE_KEY = "subscription.error.cardBrand.notBlank";
    private static final String LAST4_PATTERN_MESSAGE_KEY = "subscription.error.cardLast4.pattern";
    private static final String EXPIRY_PATTERN_MESSAGE_KEY = "subscription.error.cardExpiry.pattern";

    public PaymentCard {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException(BRAND_NOT_BLANK_MESSAGE_KEY);
        }
        if (last4 == null || !LAST4_PATTERN.matcher(last4).matches()) {
            throw new IllegalArgumentException(LAST4_PATTERN_MESSAGE_KEY);
        }
        if (expiry == null || !EXPIRY_PATTERN.matcher(expiry).matches()) {
            throw new IllegalArgumentException(EXPIRY_PATTERN_MESSAGE_KEY);
        }
    }
}
