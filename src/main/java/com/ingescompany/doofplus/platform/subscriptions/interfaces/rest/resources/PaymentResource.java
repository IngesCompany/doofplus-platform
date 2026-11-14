package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response resource representing a payment of a subscription.
 *
 * @since 1.0
 */
@Schema(description = "Response resource representing a payment of a subscription")
public record PaymentResource(
        @Schema(description = "Persistent identifier", example = "1") Long id,
        @Schema(description = "Invoice number", example = "INV-1-1") String invoiceNumber,
        @Schema(description = "Amount charged", example = "49.90") BigDecimal amount,
        @Schema(description = "ISO 4217 currency code", example = "USD") String currency,
        @Schema(description = "Transaction identifier returned by Niubiz", example = "993201") String gatewayTransactionId,
        @Schema(description = "Status", example = "APPROVED", allowableValues = {"PENDING", "APPROVED", "REJECTED"}) String status,
        @Schema(description = "Date and time when the gateway processed the payment", example = "2026-10-27T10:15:00") LocalDateTime processedAt) {
}
