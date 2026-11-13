package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CollaborationTaskCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CollaborationTaskQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CollaborationTaskResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCollaborationTaskResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CollaborationTaskResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCollaborationTaskCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/collaboration-tasks", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Collaboration Tasks", description = "Collaboration task management endpoints")
public class CollaborationTaskController {

    private final CollaborationTaskCommandService commandService;
    private final CollaborationTaskQueryService queryService;

    public CollaborationTaskController(
            CollaborationTaskCommandService commandService,
            CollaborationTaskQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a collaboration task")
    public ResponseEntity<?> createCollaborationTask(
            @RequestBody CreateCollaborationTaskResource resource) {

        var command = CreateCollaborationTaskCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CollaborationTaskResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get all collaboration tasks")
    public ResponseEntity<List<CollaborationTaskResource>> getAllCollaborationTasks() {

        List<CollaborationTaskResource> resources = queryService.handle(
                        new GetAllCollaborationTasksQuery())
                .stream()
                .map(CollaborationTaskResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}