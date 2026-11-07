package com.ingescompany.doofplus.platform.monitoring.interfaces.rest;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.SensorCommandService;
import com.ingescompany.doofplus.platform.monitoring.application.queryservices.SensorQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsByEquipmentCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetSensorByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateSensorResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.SensorResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateSensorResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.CreateSensorCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.SensorResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.UpdateSensorCommandFromResourceAssembler;
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
 * REST controller that exposes sensor resources and IoT telemetry configuration endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sensors", description = "IoT telemetry sensors management endpoints")
public class SensorsController {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;

    public SensorsController(SensorCommandService sensorCommandService, SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    /**
     * Retrieves all registered IoT sensors, optionally filtered by equipment code.
     *
     * @param equipmentCode optional filter by equipment code
     * @return List of {@link SensorResource}
     */
    @GetMapping
    @Operation(summary = "Get sensors", description = "Retrieves all registered sensors, optionally filtered by equipment code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensors retrieved successfully")
    })
    public ResponseEntity<List<SensorResource>> getAllSensors(
            @RequestParam(required = false) @Parameter(description = "Filter by equipment code") String equipmentCode
    ) {
        var sensors = (equipmentCode != null && !equipmentCode.isBlank())
                ? sensorQueryService.handle(new GetAllSensorsByEquipmentCodeQuery(equipmentCode))
                : sensorQueryService.handle(new GetAllSensorsQuery());

        var resources = sensors.stream()
                .map(SensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves a sensor by persistent identifier.
     *
     * @param id sensor ID
     * @return {@link SensorResource} or 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get sensor by ID", description = "Retrieves a sensor by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor found",
                    content = @Content(schema = @Schema(implementation = SensorResource.class))),
            @ApiResponse(responseCode = "404", description = "Sensor not found")
    })
    public ResponseEntity<?> getSensorById(
            @PathVariable @Parameter(description = "Sensor unique identifier", example = "1", required = true) Long id
    ) {
        var sensorOpt = sensorQueryService.handle(new GetSensorByIdQuery(id));
        if (sensorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "SENSOR_NOT_FOUND", "message", "Sensor with id " + id + " not found"));
        }
        var resource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensorOpt.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Registers a new sensor.
     *
     * @param resource creation payload
     * @return created {@link SensorResource}
     */
    @PostMapping
    @Operation(summary = "Create sensor", description = "Registers a new IoT sensor with ThingsBoard device binding.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sensor registered successfully",
                    content = @Content(schema = @Schema(implementation = SensorResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - sensor code or device ID already exists")
    })
    public ResponseEntity<?> createSensor(@RequestBody CreateSensorResource resource) {
        var command = CreateSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = sensorCommandService.handle(command);
        return result.fold(
                sensor -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(SensorResourceFromEntityAssembler.toResourceFromEntity(sensor)),
                error -> ResponseEntity.status(error.contains("already") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", error))
        );
    }

    /**
     * Updates an existing sensor (e.g. assigning batch or changing limits).
     *
     * @param id sensor ID
     * @param resource update payload
     * @return updated {@link SensorResource}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update sensor", description = "Updates status, limits, or assigned batch of a sensor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor updated successfully",
                    content = @Content(schema = @Schema(implementation = SensorResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Sensor not found")
    })
    public ResponseEntity<?> updateSensor(
            @PathVariable @Parameter(description = "Sensor unique identifier", example = "1", required = true) Long id,
            @RequestBody UpdateSensorResource resource
    ) {
        var command = UpdateSensorCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = sensorCommandService.handle(command);
        return result.fold(
                sensor -> ResponseEntity.ok(SensorResourceFromEntityAssembler.toResourceFromEntity(sensor)),
                error -> ResponseEntity.status(error.contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", error))
        );
    }
}
