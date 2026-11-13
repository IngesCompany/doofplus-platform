package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.OrganizationCommandService;
import com.ingescompany.doofplus.platform.organizations.application.queryservices.OrganizationQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.AddFacilityResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.FacilityResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.AddFacilityCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.FacilityResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the facilities of the organizations.
 */
@RestController
@RequestMapping(value = "/api/v1/facilities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Facilities", description = "Facility management endpoints")
public class FacilitiesController {

    private final OrganizationCommandService commandService;
    private final OrganizationQueryService queryService;

    public FacilitiesController(OrganizationCommandService commandService,
                                OrganizationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Add a facility to an organization")
    public ResponseEntity<?> addFacility(@RequestBody AddFacilityResource resource) {
        var command = AddFacilityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                facility -> FacilityResourceFromEntityAssembler.toResourceFromEntity(command.organizationId(), facility),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get every facility, or the ones of an organization")
    public ResponseEntity<List<FacilityResource>> getAllFacilities(
            @RequestParam(required = false) Long organizationId) {
        var organizations = organizationId == null
                ? queryService.handle(new GetAllOrganizationsQuery())
                : queryService.handle(new GetOrganizationByIdQuery(organizationId)).stream().toList();
        var resources = organizations.stream()
                .flatMap(organization -> organization.getFacilities().stream()
                        .map(facility -> FacilityResourceFromEntityAssembler.toResourceFromEntity(
                                organization.getId(), facility)))
                .toList();
        return ResponseEntity.ok(resources);
    }
}
