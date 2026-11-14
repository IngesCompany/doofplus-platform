package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

/**
 * Response resource representing a subscription.
 *
 * @since 1.0
 */
@Schema(description = "Response resource representing a subscription")
public record SubscriptionResource(
        @Schema(description = "Persistent identifier", example = "1") Long id,
        @Schema(description = "Organization identifier", example = "1") Long organizationId,
        @Schema(description = "Plan identifier", example = "1") Long planId,
        @Schema(description = "Billing cycle", example = "MONTHLY") String billingCycle,
        @Schema(description = "Status", example = "ACTIVE") String status,
        @Schema(description = "Whether the subscription renews automatically", example = "true") boolean autoRenew,
        @Schema(description = "Card brand", example = "Visa") String cardBrand,
        @Schema(description = "Last four digits of the card", example = "4242") String cardLast4,
        @Schema(description = "Card expiry", example = "08/2028") String cardExpiry,
        @Schema(description = "Email that receives the invoices", example = "billing@andinos.com.pe") String billingEmail,
        @Schema(description = "Start of the current billing period", example = "2026-10-27") LocalDate currentPeriodStart,
        @Schema(description = "End of the current billing period", example = "2026-11-27") LocalDate currentPeriodEnd,
        @Schema(description = "Payments of the subscription") List<PaymentResource> payments) {
}
