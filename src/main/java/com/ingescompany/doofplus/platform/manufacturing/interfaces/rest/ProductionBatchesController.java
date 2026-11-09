package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;
import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionBatchCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionBatchQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
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
    public ResponseEntity<ProductionBatchResource> getBatchById(@PathVariable Long batchId) {
        var batchOptional = queryService.handle(new GetBatchByIdQuery(batchId));
        return batchOptional
                .map(batch -> ResponseEntity.ok(ProductionBatchResourceFromEntityAssembler.toResourceFromEntity(batch)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    @Operation(summary = "Get all production batches or filter by batch number")
    public ResponseEntity<?> getBatches(@RequestParam(required = false) String batchNumber) {
        if (batchNumber != null && !batchNumber.isBlank()) {
            var batchOptional = queryService.handle(new GetBatchByNumberQuery(new BatchNumber(batchNumber)));
            return batchOptional
                    .map(batch -> ResponseEntity.ok(ProductionBatchResourceFromEntityAssembler.toResourceFromEntity(batch)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        List<ProductionBatchResource> batches = queryService.handle(new GetAllBatchesQuery())
                .stream()
                .map(ProductionBatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(batches);
    }
    @PostMapping("/{batchId}/close")
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
}
