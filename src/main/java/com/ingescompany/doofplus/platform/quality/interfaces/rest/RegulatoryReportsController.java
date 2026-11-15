package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.RegulatoryReportCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.RegulatoryReportQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetRegulatoryReportByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateRegulatoryReportResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RegulatoryReportResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateRegulatoryReportCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.RegulatoryReportResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the regulatory reports, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/regulatory-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Regulatory reports", description = "Regulatory reports of Quality and Compliance")
public class RegulatoryReportsController {

    private final RegulatoryReportCommandService commandService;
    private final RegulatoryReportQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public RegulatoryReportsController(RegulatoryReportCommandService commandService, RegulatoryReportQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a regulatory report.
     *
     * @param resource the data of the regulatory report
     * @return 201 with the regulatory report, 400 when the data is not valid or 409 when the code is taken
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a regulatory report")
    public ResponseEntity<?> createRegulatoryReport(@RequestBody CreateRegulatoryReportResource resource) {
        var command = CreateRegulatoryReportCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                RegulatoryReportResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the regulatory reports.
     *
     * @return 200 with the regulatory reports
     */
    @GetMapping
    @Operation(summary = "Get the regulatory reports")
    public ResponseEntity<List<RegulatoryReportResource>> getRegulatoryReports() {
        var resources = queryService.handle(new GetAllRegulatoryReportsQuery()).stream()
                .map(RegulatoryReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a regulatory report by its identifier.
     *
     * @param regulatoryReportId the identifier
     * @return 200 with the regulatory report, or 404
     */
    @GetMapping("/{regulatoryReportId}")
    @Operation(summary = "Get a regulatory report by its identifier")
    public ResponseEntity<?> getRegulatoryReportById(@PathVariable Long regulatoryReportId) {
        return queryService.handle(new GetRegulatoryReportByIdQuery(regulatoryReportId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(RegulatoryReportResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("RegulatoryReport", String.valueOf(regulatoryReportId))));
    }
}
