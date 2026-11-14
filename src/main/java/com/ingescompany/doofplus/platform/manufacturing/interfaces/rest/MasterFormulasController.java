package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.MasterFormulaCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.MasterFormulaQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ApproveMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllMasterFormulasQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetMasterFormulaByIdQuery;
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
 * Master formulas of the products (US36).
 */
@RestController
@RequestMapping(value = "/api/v1/master-formulas", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Master Formulas", description = "Master formula endpoints")
public class MasterFormulasController {
    private final MasterFormulaCommandService commandService;
    private final MasterFormulaQueryService queryService;

    public MasterFormulasController(MasterFormulaCommandService commandService, MasterFormulaQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Writes a draft master formula. 404 when the product does not exist, 409 when the version is repeated.
     */
    @PostMapping
    @Operation(summary = "Write a master formula")
    public ResponseEntity<?> createMasterFormula(@RequestBody CreateMasterFormulaResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get the master formulas, optionally of one product")
    public ResponseEntity<?> getMasterFormulas(@RequestParam(required = false) String productCode) {
        return ResponseEntity.ok(queryService.handle(new GetAllMasterFormulasQuery(productCode)).stream()
                .map(CatalogResourceAssembler::toResource).toList());
    }

    @GetMapping("/{masterFormulaId}")
    @Operation(summary = "Get a master formula by its id")
    public ResponseEntity<?> getMasterFormulaById(@PathVariable Long masterFormulaId) {
        return queryService.handle(new GetMasterFormulaByIdQuery(masterFormulaId))
                .<ResponseEntity<?>>map(formula -> ResponseEntity.ok(CatalogResourceAssembler.toResource(formula)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("MasterFormula", masterFormulaId.toString())));
    }

    /**
     * Approves a draft or pending formula; the version approved before becomes locked.
     */
    @PostMapping("/{masterFormulaId}/approval")
    @Operation(summary = "Approve a master formula")
    public ResponseEntity<?> approveMasterFormula(@PathVariable Long masterFormulaId,
                                                  @RequestBody ApproveMasterFormulaResource resource) {
        var result = commandService.handle(new ApproveMasterFormulaCommand(masterFormulaId, resource.approvedBy()));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }
}
