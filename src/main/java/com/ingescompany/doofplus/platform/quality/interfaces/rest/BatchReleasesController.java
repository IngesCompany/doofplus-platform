package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.BatchReleaseCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.BatchReleaseQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllBatchReleasesQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.ApproveBatchReleaseResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.BatchReleaseResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RejectBatchReleaseResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RequestBatchReleaseResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.ApproveBatchReleaseCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.BatchReleaseResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.RejectBatchReleaseCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.RequestBatchReleaseCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;

@RestController
@RequestMapping(value = "/api/v1/batch-releases", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Batch Releases", description = "Batch release management endpoints")
public class BatchReleasesController {

    private final BatchReleaseCommandService commandService;
    private final BatchReleaseQueryService queryService;

    public BatchReleasesController(
            BatchReleaseCommandService commandService,
            BatchReleaseQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Request a batch release")
    public ResponseEntity<?> requestBatchRelease(
            @RequestBody RequestBatchReleaseResource resource) {

        var command = RequestBatchReleaseCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchReleaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{releaseId}/approval")
    @Operation(summary = "Approve a batch release")
    public ResponseEntity<?> approveBatchRelease(
            @PathVariable Long releaseId,
            @RequestBody ApproveBatchReleaseResource resource) {

        var command = ApproveBatchReleaseCommandFromResourceAssembler
                .toCommandFromResource(releaseId, resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchReleaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/{releaseId}/rejection")
    @Operation(summary = "Reject a batch release")
    public ResponseEntity<?> rejectBatchRelease(
            @PathVariable Long releaseId,
            @RequestBody RejectBatchReleaseResource resource) {

        var command = RejectBatchReleaseCommandFromResourceAssembler
                .toCommandFromResource(releaseId, resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchReleaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/{releaseId}")
    @Operation(summary = "Get batch release by ID")
    public ResponseEntity<?> getBatchReleaseById(
            @PathVariable Long releaseId) {

        var releaseOptional = queryService.handle(
                new GetBatchReleaseByIdQuery(releaseId));

        return releaseOptional
                .<ResponseEntity<?>>map(release -> ResponseEntity.ok(
                        BatchReleaseResourceFromEntityAssembler.toResourceFromEntity(release)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("BatchRelease", releaseId.toString())));
    }

    @GetMapping
    @Operation(summary = "Get all batch releases or filter them by production batch")
    public ResponseEntity<List<BatchReleaseResource>> getAllBatchReleases(
            @RequestParam(required = false) Long batchId) {

        var releases = batchId != null
                ? queryService.handle(new GetBatchReleaseByBatchIdQuery(batchId)).stream()
                : queryService.handle(new GetAllBatchReleasesQuery()).stream();

        List<BatchReleaseResource> resources = releases
                .map(BatchReleaseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}