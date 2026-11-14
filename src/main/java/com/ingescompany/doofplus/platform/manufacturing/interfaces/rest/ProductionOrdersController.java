package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionOrderCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionOrderQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductionOrdersQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductionOrderByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.*;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.CatalogResourceAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Production orders and their operations (US57).
 */
@RestController
@RequestMapping(value = "/api/v1/production-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Production Orders", description = "Production order endpoints")
public class ProductionOrdersController {
    private final ProductionOrderCommandService commandService;
    private final ProductionOrderQueryService queryService;

    public ProductionOrdersController(ProductionOrderCommandService commandService,
                                      ProductionOrderQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Plans a production order with an approved master formula.
     */
    @PostMapping
    @Operation(summary = "Plan a production order")
    public ResponseEntity<?> createProductionOrder(@RequestBody CreateProductionOrderResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all production orders")
    public ResponseEntity<?> getProductionOrders() {
        return ResponseEntity.ok(queryService.handle(new GetAllProductionOrdersQuery()).stream()
                .map(CatalogResourceAssembler::toResource).toList());
    }

    @GetMapping("/{productionOrderId}")
    @Operation(summary = "Get a production order by its id")
    public ResponseEntity<?> getProductionOrderById(@PathVariable Long productionOrderId) {
        return queryService.handle(new GetProductionOrderByIdQuery(productionOrderId))
                .<ResponseEntity<?>>map(order -> ResponseEntity.ok(CatalogResourceAssembler.toResource(order)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("ProductionOrder", productionOrderId.toString())));
    }

    /**
     * Changes the status of a production order.
     */
    @PutMapping("/{productionOrderId}/status")
    @Operation(summary = "Change the status of a production order")
    public ResponseEntity<?> changeStatus(@PathVariable Long productionOrderId,
                                          @RequestBody ChangeProductionOrderStatusResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(productionOrderId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }

    /**
     * Records the progress of an operation.
     */
    @PutMapping("/{productionOrderId}/operations/{sequence}")
    @Operation(summary = "Record the progress of an operation")
    public ResponseEntity<?> updateOperation(@PathVariable Long productionOrderId, @PathVariable Integer sequence,
                                             @RequestBody UpdateOperationResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(productionOrderId, sequence, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }
}
