package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.RawMaterialLotCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.RawMaterialLotQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.AllocateRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ApproveRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RejectRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllRawMaterialLotsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetRawMaterialLotByIdQuery;
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
 * Raw material lots: receipt, QC inspection, approval or rejection and allocation to a batch (US58, US17).
 */
@RestController
@RequestMapping(value = "/api/v1/material-lots", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Material Lots", description = "Raw material lot endpoints")
public class RawMaterialLotsController {
    private final RawMaterialLotCommandService commandService;
    private final RawMaterialLotQueryService queryService;

    public RawMaterialLotsController(RawMaterialLotCommandService commandService, RawMaterialLotQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Receives a lot; it goes to sampling. A repeated code answers 409.
     */
    @PostMapping
    @Operation(summary = "Receive a raw material lot")
    public ResponseEntity<?> receiveLot(@RequestBody ReceiveRawMaterialLotResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all raw material lots")
    public ResponseEntity<?> getLots() {
        return ResponseEntity.ok(queryService.handle(new GetAllRawMaterialLotsQuery()).stream()
                .map(CatalogResourceAssembler::toResource).toList());
    }

    @GetMapping("/{rawMaterialLotId}")
    @Operation(summary = "Get a raw material lot by its id")
    public ResponseEntity<?> getLotById(@PathVariable Long rawMaterialLotId) {
        return queryService.handle(new GetRawMaterialLotByIdQuery(rawMaterialLotId))
                .<ResponseEntity<?>>map(lot -> ResponseEntity.ok(CatalogResourceAssembler.toResource(lot)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("RawMaterialLot", rawMaterialLotId.toString())));
    }

    @PutMapping("/{rawMaterialLotId}/inspection")
    @Operation(summary = "Record the QC inspection of a lot")
    public ResponseEntity<?> inspectLot(@PathVariable Long rawMaterialLotId,
                                        @RequestBody InspectRawMaterialLotResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(rawMaterialLotId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }

    @PostMapping("/{rawMaterialLotId}/approval")
    @Operation(summary = "Approve a lot in inspection")
    public ResponseEntity<?> approveLot(@PathVariable Long rawMaterialLotId) {
        var result = commandService.handle(new ApproveRawMaterialLotCommand(rawMaterialLotId));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }

    @PostMapping("/{rawMaterialLotId}/rejection")
    @Operation(summary = "Reject a lot in inspection")
    public ResponseEntity<?> rejectLot(@PathVariable Long rawMaterialLotId) {
        var result = commandService.handle(new RejectRawMaterialLotCommand(rawMaterialLotId));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }

    @PostMapping("/{rawMaterialLotId}/allocation")
    @Operation(summary = "Allocate an approved lot to a batch")
    public ResponseEntity<?> allocateLot(@PathVariable Long rawMaterialLotId,
                                         @RequestBody AllocateRawMaterialLotResource resource) {
        var result = commandService.handle(new AllocateRawMaterialLotCommand(rawMaterialLotId, resource.batchCode()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }
}
