package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.OrganizationCommandService;
import com.ingescompany.doofplus.platform.organizations.application.queryservices.OrganizationQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.AddPlantCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.AddPlantResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.OrganizationResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.RegisterOrganizationResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.OrganizationResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.RegisterOrganizationCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the Organizations resources.
 */
@RestController
@RequestMapping(value = "/api/v1/organizations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Organizations", description = "Organization registration and plant management endpoints")
public class OrganizationsController {

    private final OrganizationCommandService commandService;
    private final OrganizationQueryService queryService;

    public OrganizationsController(OrganizationCommandService commandService,
                                   OrganizationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Register a new organization")
    public ResponseEntity<?> registerOrganization(@RequestBody RegisterOrganizationResource resource) {
        var command = RegisterOrganizationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OrganizationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping("/{organizationId}")
    @Operation(summary = "Get an organization by id")
    public ResponseEntity<OrganizationResource> getOrganizationById(@PathVariable Long organizationId) {
        return queryService.handle(new GetOrganizationByIdQuery(organizationId))
                .map(organization -> ResponseEntity.ok(
                        OrganizationResourceFromEntityAssembler.toResourceFromEntity(organization)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{organizationId}/plants")
    @Operation(summary = "Add a plant to an organization")
    public ResponseEntity<?> addPlant(@PathVariable Long organizationId, @RequestBody AddPlantResource resource) {
        var command = new AddPlantCommand(organizationId, resource.name(), resource.address());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OrganizationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}
