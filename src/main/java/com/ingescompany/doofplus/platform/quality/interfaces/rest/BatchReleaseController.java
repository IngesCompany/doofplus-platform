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

@RestController
@RequestMapping(value = "/api/v1/batch-releases", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Batch Releases", description = "Batch release management endpoints")
public class BatchReleaseController {

    private final BatchReleaseCommandService commandService;
    private final BatchReleaseQueryService queryService;

    public BatchReleaseController(
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

    @PutMapping("/{releaseId}/approve")
    @Operation(summary = "Approve a batch release")
    public ResponseEntity<?> approveBatchRelease(
            @PathVariable Long releaseId,
            @RequestBody ApproveBatchReleaseResource resource) {

        var command = ApproveBatchReleaseCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchReleaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PutMapping("/{releaseId}/reject")
    @Operation(summary = "Reject a batch release")
    public ResponseEntity<?> rejectBatchRelease(
            @PathVariable Long releaseId,
            @RequestBody RejectBatchReleaseResource resource) {

        var command = RejectBatchReleaseCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                BatchReleaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/{releaseId}")
    @Operation(summary = "Get batch release by ID")
    public ResponseEntity<BatchReleaseResource> getBatchReleaseById(
            @PathVariable Long releaseId) {

        var releaseOptional = queryService.handle(
                new GetBatchReleaseByIdQuery(releaseId));

        return releaseOptional
                .map(release -> ResponseEntity.ok(
                        BatchReleaseResourceFromEntityAssembler.toResourceFromEntity(release)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-batch/{batchId}")
    @Operation(summary = "Get batch releases by batch ID")
    public ResponseEntity<List<BatchReleaseResource>> getBatchReleasesByBatchId(
            @PathVariable Long batchId) {

        List<BatchReleaseResource> resources = queryService.handle(
                        new GetBatchReleaseByBatchIdQuery(batchId))
                .stream()
                .map(BatchReleaseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    @Operation(summary = "Get all batch releases")
    public ResponseEntity<List<BatchReleaseResource>> getAllBatchReleases() {

        List<BatchReleaseResource> resources = queryService.handle(
                        new GetAllBatchReleasesQuery())
                .stream()
                .map(BatchReleaseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}