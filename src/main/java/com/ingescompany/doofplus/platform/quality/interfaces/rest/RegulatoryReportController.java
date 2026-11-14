package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.RegulatoryReportCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.RegulatoryReportQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateRegulatoryReportResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RegulatoryReportResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateRegulatoryReportCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.RegulatoryReportResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/regulatory-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Regulatory Reports", description = "Regulatory report management endpoints")
public class RegulatoryReportController {

    private final RegulatoryReportCommandService commandService;
    private final RegulatoryReportQueryService queryService;

    public RegulatoryReportController(
            RegulatoryReportCommandService commandService,
            RegulatoryReportQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a regulatory report")
    public ResponseEntity<?> createReport(
            @RequestBody CreateRegulatoryReportResource resource) {

        var command =
                CreateRegulatoryReportCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RegulatoryReportResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get all regulatory reports")
    public ResponseEntity<List<RegulatoryReportResource>> getAllReports() {

        List<RegulatoryReportResource> reports =
                queryService.handle(new GetAllRegulatoryReportsQuery())
                        .stream()
                        .map(RegulatoryReportResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();

        return ResponseEntity.ok(reports);
    }
}