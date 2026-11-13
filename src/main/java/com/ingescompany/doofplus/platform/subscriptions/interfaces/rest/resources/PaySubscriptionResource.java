package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request resource to pay the next billing period of a subscription.
 *
 * @since 1.0
 */
@Schema(description = "Request resource to pay the next billing period of a subscription")
public record PaySubscriptionResource(
        @Schema(description = "Card token created by the Niubiz payment form", example = "7B4E2A1C9D8F4A6B", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{payment.error.cardToken.notBlank}")
        String cardToken) {
}
