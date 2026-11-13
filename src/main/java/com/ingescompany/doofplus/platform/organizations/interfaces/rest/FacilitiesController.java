package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.OrganizationCommandService;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.AddFacilityResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.AddFacilityCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.FacilityResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the facilities of the organizations.
 */
@RestController
@RequestMapping(value = "/api/v1/facilities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Facilities", description = "Facility management endpoints")
public class FacilitiesController {

    private final OrganizationCommandService commandService;

    public FacilitiesController(OrganizationCommandService commandService) {
        this.commandService = commandService;
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
}
