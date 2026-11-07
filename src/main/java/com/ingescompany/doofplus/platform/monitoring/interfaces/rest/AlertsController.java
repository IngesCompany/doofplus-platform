package com.ingescompany.doofplus.platform.monitoring.interfaces.rest;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.AlertCommandService;
import com.ingescompany.doofplus.platform.monitoring.application.queryservices.AlertQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllAlertsByBatchCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllAlertsBySensorCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllAlertsQuery;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateAlertResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateAlertResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.UpdateAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller that exposes monitoring alert resources and lifecycle management endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Monitoring alerts acknowledgment and tracking endpoints")
public class AlertsController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertsController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    /**
     * Retrieves all monitoring alerts, optionally filtered by sensorCode or batchCode.
     *
     * @param sensorCode optional filter by sensor code
     * @param batchCode optional filter by batch code
     * @return List of {@link AlertResource}
     */
    @GetMapping
    @Operation(summary = "Get alerts", description = "Retrieves all monitoring alerts, optionally filtered by sensor or batch code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAllAlerts(
            @RequestParam(required = false) @Parameter(description = "Filter by sensor code") String sensorCode,
            @RequestParam(required = false) @Parameter(description = "Filter by batch code") String batchCode
    ) {
        var alerts = (sensorCode != null && !sensorCode.isBlank())
                ? alertQueryService.handle(new GetAllAlertsBySensorCodeQuery(sensorCode))
                : (batchCode != null && !batchCode.isBlank())
                ? alertQueryService.handle(new GetAllAlertsByBatchCodeQuery(batchCode))
                : alertQueryService.handle(new GetAllAlertsQuery());

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves an alert by persistent identifier.
     *
     * @param id alert ID
     * @return {@link AlertResource} or 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID", description = "Retrieves a monitoring alert by unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert found",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<?> getAlertById(
            @PathVariable @Parameter(description = "Alert unique identifier", example = "1", required = true) Long id
    ) {
        var alertOpt = alertQueryService.handle(new GetAlertByIdQuery(id));
        if (alertOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "ALERT_NOT_FOUND", "message", "Alert with id " + id + " not found"));
        }
        var resource = AlertResourceFromEntityAssembler.toResourceFromEntity(alertOpt.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Creates a new monitoring alert.
     *
     * @param resource creation payload
     * @return created {@link AlertResource}
     */
    @PostMapping
    @Operation(summary = "Create alert", description = "Creates a new monitoring alert.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alert created successfully",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - alert code already exists")
    })
    public ResponseEntity<?> createAlert(@RequestBody CreateAlertResource resource) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertCommandService.handle(command);
        return result.fold(
                alert -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(AlertResourceFromEntityAssembler.toResourceFromEntity(alert)),
                error -> ResponseEntity.status(error.contains("already exists") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", error))
        );
    }

    /**
     * Updates an existing alert (e.g. acknowledgment or deviation linking).
     *
     * @param id alert ID
     * @param resource update payload
     * @return updated {@link AlertResource}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update alert", description = "Updates status, notes, or linked deviation record of an alert.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert updated successfully",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<?> updateAlert(
            @PathVariable @Parameter(description = "Alert unique identifier", example = "1", required = true) Long id,
            @RequestBody UpdateAlertResource resource
    ) {
        var command = UpdateAlertCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = alertCommandService.handle(command);
        return result.fold(
                alert -> ResponseEntity.ok(AlertResourceFromEntityAssembler.toResourceFromEntity(alert)),
                error -> ResponseEntity.status(error.contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", error))
        );
    }
}
