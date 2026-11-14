package com.ingescompany.doofplus.platform.monitoring.interfaces.rest;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.ReadingCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.RecordTelemetryCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.TelemetryResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.ReadingResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Receives the telemetry of the sensors from the rule chain of ThingsBoard (TS04).
 * ThingsBoard sends the API key in the {@code X-Api-Key} header; it is compared in constant time with
 * {@code thingsboard.api-key}, which comes from the {@code THINGSBOARD_API_KEY} environment variable.
 */
@RestController
@RequestMapping(value = "/api/v1/telemetry", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Telemetry", description = "Telemetry sent by ThingsBoard")
public class TelemetryController {
    public static final String API_KEY_HEADER = "X-Api-Key";

    private final ReadingCommandService readingCommandService;
    private final String apiKey;

    public TelemetryController(ReadingCommandService readingCommandService,
                               @Value("${thingsboard.api-key:}") String apiKey) {
        this.readingCommandService = readingCommandService;
        this.apiKey = apiKey;
    }

    /**
     * Stores the reading of the sensor linked to the device. Answers 202 Accepted, 401 without a valid
     * API key (always 401 when no key is configured), 400 with invalid data and 404 for an unknown device.
     */
    @PostMapping
    @Operation(summary = "Receive a reading from ThingsBoard")
    public ResponseEntity<?> receiveTelemetry(@RequestHeader(value = API_KEY_HEADER, required = false) String key,
                                              @RequestBody TelemetryResource resource) {
        if (!isValidApiKey(key)) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                    ApplicationError.unauthorized("telemetry", "monitoring.telemetry.error.apiKey.invalid"));
        }
        var command = new RecordTelemetryCommand(new DeviceId(resource.deviceId()), resource.value(), resource.recordedAt());
        var result = readingCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ReadingResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.ACCEPTED);
    }

    private boolean isValidApiKey(String key) {
        if (apiKey == null || apiKey.isBlank() || key == null || key.isBlank()) return false;
        return MessageDigest.isEqual(key.getBytes(StandardCharsets.UTF_8), apiKey.getBytes(StandardCharsets.UTF_8));
    }
}
