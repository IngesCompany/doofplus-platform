package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;
import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionBatchCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionBatchQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ReleaseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ResumeBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.StartBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateBatchProgressCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllBatchesQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByNumberQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.CloseProductionBatchResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.CreateProductionBatchResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.ProductionBatchResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.RegisterIncidentResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.ReleaseProductionBatchResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.UpdateBatchProgressResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.CreateProductionBatchCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.IncidentResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.ProductionBatchResourceFromEntityAssembler;
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

/**
 * REST controller that exposes the Production Batches resources and administration endpoints
 */
@RestController
@RequestMapping(value = "/api/v1/production-batches", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Production Batches", description = "Manufacturing production batch management endpoints")
public class ProductionBatchesController {

    private final ProductionBatchCommandService commandService;
    private final ProductionBatchQueryService queryService;

    public ProductionBatchesController(
            ProductionBatchCommandService commandService,
            ProductionBatchQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }
    @PostMapping
    @Operation(summary = "Create a new production batch")
    public ResponseEntity<?> createBatch(@RequestBody CreateProductionBatchResource resource) {
        var command = CreateProductionBatchCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductionBatchResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
    @GetMapping("/{batchId}")
    @Operation(summary = "Get production batch by ID")
    public ResponseEntity<?> getBatchById(@PathVariable Long batchId) {
        var batchOptional = queryService.handle(new GetBatchByIdQuery(batchId));
        return batchOptional
                .<ResponseEntity<?>>map(batch -> ResponseEntity.ok(ProductionBatchResourceFromEntityAssembler.toResourceFromEntity(batch)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("ProductionBatch", batchId.toString())));
    }
    @GetMapping
    @Operation(summary = "Get all production batches or filter them by batch number")
    public ResponseEntity<List<ProductionBatchResource>> getBatches(@RequestParam(required = false) String batchNumber) {
        var batches = batchNumber != null && !batchNumber.isBlank()
                ? queryService.handle(new GetBatchByNumberQuery(new BatchNumber(batchNumber))).stream()
                : queryService.handle(new GetAllBatchesQuery()).stream();
        List<ProductionBatchResource> resources = batches
                .map(ProductionBatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{batchId}/closure")
    @Operation(summary = "Close a production batch")
    public ResponseEntity<?> closeBatch(
            @PathVariable Long batchId,
            @RequestBody CloseProductionBatchResource resource) {
        var command = new CloseBatchCommand(
                batchId,
                new Quantity(resource.manufacturedQuantityValue(), resource.manufacturedQuantityUnit())
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductionBatchResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
    @PostMapping("/{batchId}/incidents")
    @Operation(summary = "Register an incident for a production batch")
    public ResponseEntity<?> registerIncident(
            @PathVariable Long batchId,
            @RequestBody RegisterIncidentResource resource) {
        var command = new RegisterIncidentCommand(
                batchId,
                resource.description(),
                IncidentSeverity.valueOf(resource.severity().toUpperCase())
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                IncidentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Updates the progress of a production batch (0 to 100), as the Web App shows it.
     *
     * @param batchId  the batch ID
     * @param resource the new progress
     * @return the updated batch, 400 when the progress is out of range, or 404 when the batch does not exist
     */
    @PatchMapping("/{batchId}/progress")
    @Operation(summary = "Update the progress of a production batch")
    public ResponseEntity<?> updateProgress(
            @PathVariable Long batchId,
            @RequestBody UpdateBatchProgressResource resource) {
        var result = commandService.handle(new UpdateBatchProgressCommand(batchId, resource.progress()));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductionBatchResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Starts a planned production batch.
     *
     * @param batchId the batch ID
     * @return the batch in progress, 404 when it does not exist, or 409 when it is not planned
     */
    @PostMapping("/{batchId}/execution")
    @Operation(summary = "Start a planned production batch")
    public ResponseEntity<?> startBatch(@PathVariable Long batchId) {
        var result = commandService.handle(new StartBatchCommand(batchId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ProductionBatchResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Resumes a production batch that is on hold.
     *
     * @param batchId the batch ID
     * @return the batch in progress, 404 when it does not exist, or 409 when it is not on hold
     */
    @PostMapping("/{batchId}/resumption")
    @Operation(summary = "Resume a production batch that is on hold")
    public ResponseEntity<?> resumeBatch(@PathVariable Long batchId) {
        var result = commandService.handle(new ResumeBatchCommand(batchId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ProductionBatchResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Releases a production batch for distribution (QA).
     *
     * @param batchId  the batch ID
     * @param resource the name of the person who releases the batch
     * @return the released batch, 400 without releasedBy, 404 when it does not exist,
     *         or 409 when the release was not requested
     */
    @PostMapping("/{batchId}/distribution-release")
    @Operation(summary = "Release a production batch for distribution")
    public ResponseEntity<?> releaseBatch(@PathVariable Long batchId,
                                          @RequestBody ReleaseProductionBatchResource resource) {
        var result = commandService.handle(new ReleaseBatchCommand(batchId, resource.releasedBy()));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ProductionBatchResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
