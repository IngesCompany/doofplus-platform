package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.SubscriptionCommandService;
import com.ingescompany.doofplus.platform.subscriptions.application.queryservices.SubscriptionQueryService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.ActivateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.PaySubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllSubscriptionsByOrganizationIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.PaySubscriptionResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Inbound service in the interface layer for subscriptions.
 * POST operations delegate to {@link SubscriptionCommandService}; GET operations delegate to {@link SubscriptionQueryService}.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Endpoints for subscriptions")
public class SubscriptionsController {
    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Selects a plan for an organization (US51). The subscription starts as pending.
     *
     * @param resource the request payload
     * @return the created subscription, not found when the plan does not exist, or conflict when the organization is already subscribed
     */
    @Operation(summary = "Create a subscription", description = "Selects a plan for an organization; the subscription starts as PENDING")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Plan not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - the organization already has a pending or active subscription",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createSubscription(@Valid @RequestBody CreateSubscriptionResource resource) {
        log.debug("POST /api/v1/subscriptions - organizationId={}, planId={}", resource.organizationId(), resource.planId());
        var result = subscriptionCommandService.handle(CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, SubscriptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Pays the next billing period with Niubiz (US52). An approved payment activates a pending
     * subscription or renews an active one.
     *
     * @param id       Subscription ID
     * @param resource the card token created by the Niubiz payment form
     * @return the paid subscription, not found, or a business rule violation when it cannot be paid or the charge is rejected
     */
    @Operation(summary = "Pay a subscription", description = "Charges the plan price with Niubiz; an approved payment activates a PENDING subscription or renews an ACTIVE one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment approved",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Subscription or plan not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation - the subscription is not pending or active, or Niubiz rejected the charge",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "503", description = "Niubiz is not available; the payment is not recorded",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("{id}/payments")
    public ResponseEntity<?> paySubscription(@PathVariable Long id, @Valid @RequestBody PaySubscriptionResource resource) {
        log.debug("POST /api/v1/subscriptions/{}/payments", id);
        var result = subscriptionCommandService.handle(new PaySubscriptionCommand(id, resource.cardToken()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, SubscriptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Activates a pending subscription once the payment is authorized (US52).
     *
     * @param id Subscription ID
     * @return the active subscription, not found, or a business rule violation when it is not pending
     */
    @Operation(summary = "Activate a subscription", description = "Activates a PENDING subscription once the payment is authorized")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription activated",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation - the subscription is not pending",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("{id}/activation")
    public ResponseEntity<?> activateSubscription(@PathVariable Long id) {
        log.debug("POST /api/v1/subscriptions/{}/activation", id);
        var result = subscriptionCommandService.handle(new ActivateSubscriptionCommand(id));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, SubscriptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Renews an active subscription for one more billing period (US53).
     *
     * @param id Subscription ID
     * @return the renewed subscription, not found, or a business rule violation when it is not active
     */
    @Operation(summary = "Renew a subscription", description = "Extends an ACTIVE subscription for one more billing period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription renewed",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation - the subscription is not active",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("{id}/renewal")
    public ResponseEntity<?> renewSubscription(@PathVariable Long id) {
        log.debug("POST /api/v1/subscriptions/{}/renewal", id);
        var result = subscriptionCommandService.handle(new RenewSubscriptionCommand(id));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, SubscriptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Cancels an active subscription (US53).
     *
     * @param id Subscription ID
     * @return the cancelled subscription, not found, or a business rule violation when it is not active
     */
    @Operation(summary = "Cancel a subscription", description = "Cancels an ACTIVE subscription; the access stays until the end of the paid period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription cancelled",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation - the subscription is not active",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("{id}/cancellation")
    public ResponseEntity<?> cancelSubscription(@PathVariable Long id) {
        log.debug("POST /api/v1/subscriptions/{}/cancellation", id);
        var result = subscriptionCommandService.handle(new CancelSubscriptionCommand(id));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, SubscriptionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Gets a subscription by ID.
     *
     * @param id Subscription ID
     * @return the subscription resource if found or not found otherwise
     */
    @Operation(summary = "Get a subscription by ID", description = "Gets a subscription by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription found",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))),
            @ApiResponse(responseCode = "404", description = "Subscription not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getSubscriptionById(@PathVariable Long id) {
        log.debug("GET /api/v1/subscriptions/{}", id);
        var subscription = subscriptionQueryService.handle(new GetSubscriptionByIdQuery(id));
        if (subscription.isEmpty()) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                    ApplicationError.notFound("Subscription", id.toString()));
        }
        return ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get()));
    }

    /**
     * Gets the subscriptions of an organization.
     *
     * @param organizationId Organization ID
     * @return list of subscription resources
     */
    @Operation(summary = "Get subscriptions by organization", description = "Gets the subscriptions of the provided organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscriptions found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubscriptionResource.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getSubscriptionsByOrganizationId(
            @Parameter(name = "organizationId", description = "Organization ID", required = true)
            @RequestParam Long organizationId) {
        log.debug("GET /api/v1/subscriptions?organizationId={}", organizationId);
        if (organizationId <= 0) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                    ApplicationError.validationError("organizationId", "organizationId must be greater than 0"));
        }
        var subscriptions = subscriptionQueryService.handle(
                new GetAllSubscriptionsByOrganizationIdQuery(new OrganizationId(organizationId)));
        return ResponseEntity.ok(subscriptions.stream().map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
