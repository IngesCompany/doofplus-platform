package com.ingescompany.doofplus.platform.monitoring.interfaces.rest;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.ReadingCommandService;
import com.ingescompany.doofplus.platform.monitoring.application.queryservices.ReadingQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsByBatchCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsBySensorCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetReadingByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateReadingResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.ReadingResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.CreateReadingCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.ReadingResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
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

/**
 * REST controller that exposes telemetry reading resources and ingestion endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/readings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Readings", description = "IoT telemetry readings ingestion and historical queries")
public class ReadingsController {

    private final ReadingCommandService readingCommandService;
    private final ReadingQueryService readingQueryService;

    public ReadingsController(ReadingCommandService readingCommandService, ReadingQueryService readingQueryService) {
        this.readingCommandService = readingCommandService;
        this.readingQueryService = readingQueryService;
    }

    /**
     * Retrieves telemetry readings with optional filters by sensorCode or batchCode.
     *
     * @param sensorCode optional filter by sensor code
     * @param batchCode optional filter by batch code
     * @return List of {@link ReadingResource}
     */
    @GetMapping
    @Operation(summary = "Get readings", description = "Retrieves telemetry readings, optionally filtered by sensor or batch code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Readings retrieved successfully")
    })
    public ResponseEntity<List<ReadingResource>> getAllReadings(
            @RequestParam(required = false) @Parameter(description = "Filter by sensor code") String sensorCode,
            @RequestParam(required = false) @Parameter(description = "Filter by batch code") String batchCode
    ) {
        var readings = (sensorCode != null && !sensorCode.isBlank())
                ? readingQueryService.handle(new GetAllReadingsBySensorCodeQuery(sensorCode))
                : (batchCode != null && !batchCode.isBlank())
                ? readingQueryService.handle(new GetAllReadingsByBatchCodeQuery(batchCode))
                : readingQueryService.handle(new GetAllReadingsQuery());

        var resources = readings.stream()
                .map(ReadingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves a reading by persistent identifier.
     *
     * @param id reading ID
     * @return {@link ReadingResource} or 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get reading by ID", description = "Retrieves a specific telemetry reading by unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading found",
                    content = @Content(schema = @Schema(implementation = ReadingResource.class))),
            @ApiResponse(responseCode = "404", description = "Reading not found")
    })
    public ResponseEntity<?> getReadingById(
            @PathVariable @Parameter(description = "Reading unique identifier", example = "1", required = true) Long id
    ) {
        var readingOpt = readingQueryService.handle(new GetReadingByIdQuery(id));
        if (readingOpt.isEmpty()) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                    ApplicationError.notFound("Reading", id.toString())
            );
        }
        var resource = ReadingResourceFromEntityAssembler.toResourceFromEntity(readingOpt.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Ingests a new telemetry reading.
     *
     * @param resource creation payload
     * @return created {@link ReadingResource}
     */
    @PostMapping
    @Operation(summary = "Record reading", description = "Records a new telemetry reading captured from ThingsBoard.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reading recorded successfully",
                    content = @Content(schema = @Schema(implementation = ReadingResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error")
    })
    public ResponseEntity<?> createReading(@RequestBody CreateReadingResource resource) {
        var command = CreateReadingCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = readingCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ReadingResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
