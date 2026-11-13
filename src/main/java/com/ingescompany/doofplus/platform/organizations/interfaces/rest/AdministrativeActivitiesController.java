package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.queryservices.AdministrativeActivityQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllAdministrativeActivitiesQuery;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.AdministrativeActivityResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the recent administrative activity of the organizations.
 */
@RestController
@RequestMapping(value = "/api/v1/administrative-activities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Administrative Activities", description = "Recent administrative activity endpoints")
public class AdministrativeActivitiesController {

    private final AdministrativeActivityQueryService queryService;

    public AdministrativeActivitiesController(AdministrativeActivityQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get the administrative activities, the most recent first")
    public ResponseEntity<List<AdministrativeActivityResource>> getAllAdministrativeActivities() {
        var resources = queryService.handle(new GetAllAdministrativeActivitiesQuery()).stream()
                .map(activity -> new AdministrativeActivityResource(
                        activity.getId(),
                        activity.getOrganizationId(),
                        activity.getTitle(),
                        activity.getActor(),
                        activity.getOccurredAt()))
                .toList();
        return ResponseEntity.ok(resources);
    }
}
