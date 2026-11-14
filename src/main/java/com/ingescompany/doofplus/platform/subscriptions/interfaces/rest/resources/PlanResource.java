package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Response resource representing a subscription plan.
 *
 * @since 1.0
 */
@Schema(description = "Response resource representing a subscription plan")
public record PlanResource(
        @Schema(description = "Persistent identifier", example = "1") Long id,
        @Schema(description = "Plan key", example = "standard-lab") String planKey,
        @Schema(description = "Display name", example = "Standard Lab") String name,
        @Schema(description = "Monthly price", example = "199.00") BigDecimal monthlyPrice,
        @Schema(description = "Annual price", example = "1990.00") BigDecimal annualPrice,
        @Schema(description = "Currency", example = "USD") String currency,
        @Schema(description = "Maximum number of users (0 means unlimited)", example = "10") int userLimit,
        @Schema(description = "Maximum number of IoT devices (0 means unlimited)", example = "5") int iotDeviceLimit) {
}
