package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Request resource used to create a subscription plan.
 *
 * @since 1.0
 */
@Schema(description = "Request resource used to create a subscription plan")
public record CreatePlanResource(
        @Schema(description = "Unique plan key in kebab case", example = "standard-lab", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{plan.error.planKey.notBlank}")
        @Size(max = 64, message = "{plan.error.planKey.size}")
        @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "{plan.error.planKey.pattern}")
        String planKey,

        @Schema(description = "Display name", example = "Standard Lab", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{plan.error.name.notBlank}")
        String name,

        @Schema(description = "Monthly price", example = "199.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{money.error.amount.invalid}")
        @PositiveOrZero(message = "{money.error.amount.invalid}")
        BigDecimal monthlyPrice,

        @Schema(description = "Annual price", example = "1990.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{money.error.amount.invalid}")
        @PositiveOrZero(message = "{money.error.amount.invalid}")
        BigDecimal annualPrice,

        @Schema(description = "ISO 4217 currency code", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{money.error.currency.pattern}")
        @Pattern(regexp = "^[A-Z]{3}$", message = "{money.error.currency.pattern}")
        String currency,

        @Schema(description = "Maximum number of users (0 means unlimited)", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @PositiveOrZero(message = "{plan.error.limits.invalid}")
        int userLimit,

        @Schema(description = "Maximum number of IoT devices (0 means unlimited)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @PositiveOrZero(message = "{plan.error.limits.invalid}")
        int iotDeviceLimit) {
}
