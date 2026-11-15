package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CollaborationTaskCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CollaborationTaskQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCollaborationTaskByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CollaborationTaskResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCollaborationTaskResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CollaborationTaskResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCollaborationTaskCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the collaboration tasks, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/collaboration-tasks", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Collaboration tasks", description = "Collaboration tasks of Quality and Compliance")
public class CollaborationTasksController {

    private final CollaborationTaskCommandService commandService;
    private final CollaborationTaskQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public CollaborationTasksController(CollaborationTaskCommandService commandService, CollaborationTaskQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a collaboration task.
     *
     * @param resource the data of the collaboration task
     * @return 201 with the collaboration task, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a collaboration task")
    public ResponseEntity<?> createCollaborationTask(@RequestBody CreateCollaborationTaskResource resource) {
        var command = CreateCollaborationTaskCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                CollaborationTaskResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the collaboration tasks, optionally filtered by record code.
     *
     * @param recordCode the record code, or none for all
     *
     * @return 200 with the collaboration tasks
     */
    @GetMapping
    @Operation(summary = "Get the collaboration tasks")
    public ResponseEntity<List<CollaborationTaskResource>> getCollaborationTasks(@RequestParam(required = false) String recordCode) {
        var resources = queryService.handle(new GetAllCollaborationTasksQuery(recordCode)).stream()
                .map(CollaborationTaskResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a collaboration task by its identifier.
     *
     * @param collaborationTaskId the identifier
     * @return 200 with the collaboration task, or 404
     */
    @GetMapping("/{collaborationTaskId}")
    @Operation(summary = "Get a collaboration task by its identifier")
    public ResponseEntity<?> getCollaborationTaskById(@PathVariable Long collaborationTaskId) {
        return queryService.handle(new GetCollaborationTaskByIdQuery(collaborationTaskId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(CollaborationTaskResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("CollaborationTask", String.valueOf(collaborationTaskId))));
    }
}
