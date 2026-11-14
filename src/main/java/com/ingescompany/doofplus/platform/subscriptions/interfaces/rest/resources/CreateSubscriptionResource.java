package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to select a plan for an organization.
 *
 * @since 1.0
 */
@Schema(description = "Request resource used to select a plan for an organization")
public record CreateSubscriptionResource(
        @Schema(description = "Organization identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{subscription.error.organizationId.invalid}")
        @Positive(message = "{subscription.error.organizationId.invalid}")
        Long organizationId,

        @Schema(description = "Plan identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{plan.error.id.invalid}")
        @Positive(message = "{plan.error.id.invalid}")
        Long planId,

        @Schema(description = "Billing cycle", example = "MONTHLY", allowableValues = {"MONTHLY", "ANNUAL"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.billingCycle.invalid}")
        @Pattern(regexp = "^(MONTHLY|ANNUAL)$", message = "{subscription.error.billingCycle.invalid}")
        String billingCycle,

        @Schema(description = "Card brand", example = "Visa", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.cardBrand.notBlank}")
        @Size(max = 20, message = "{subscription.error.cardBrand.size}")
        String cardBrand,

        @Schema(description = "Last four digits of the card", example = "4242", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.cardLast4.pattern}")
        @Pattern(regexp = "^\\d{4}$", message = "{subscription.error.cardLast4.pattern}")
        String cardLast4,

        @Schema(description = "Card expiry in MM/yyyy format", example = "08/2028", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.cardExpiry.pattern}")
        @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{4}$", message = "{subscription.error.cardExpiry.pattern}")
        String cardExpiry,

        @Schema(description = "Email that receives the invoices", example = "billing@andinos.com.pe", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.billingEmail.notBlank}")
        @Email(message = "{subscription.error.billingEmail.pattern}")
        String billingEmail) {
}
