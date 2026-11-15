package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TaskCommentCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.TaskCommentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTaskCommentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTaskCommentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TaskCommentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateTaskCommentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.TaskCommentResourceFromEntityAssembler;
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
 * REST controller of the task comments, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/task-comments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Task comments", description = "Task comments of Quality and Compliance")
public class TaskCommentsController {

    private final TaskCommentCommandService commandService;
    private final TaskCommentQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public TaskCommentsController(TaskCommentCommandService commandService, TaskCommentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a task comment.
     *
     * @param resource the data of the task comment
     * @return 201 with the task comment, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a task comment")
    public ResponseEntity<?> createTaskComment(@RequestBody CreateTaskCommentResource resource) {
        var command = CreateTaskCommentCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                TaskCommentResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the task comments, optionally filtered by record code.
     *
     * @param recordCode the record code, or none for all
     *
     * @return 200 with the task comments
     */
    @GetMapping
    @Operation(summary = "Get the task comments")
    public ResponseEntity<List<TaskCommentResource>> getTaskComments(@RequestParam(required = false) String recordCode) {
        var resources = queryService.handle(new GetAllTaskCommentsQuery(recordCode)).stream()
                .map(TaskCommentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a task comment by its identifier.
     *
     * @param taskCommentId the identifier
     * @return 200 with the task comment, or 404
     */
    @GetMapping("/{taskCommentId}")
    @Operation(summary = "Get a task comment by its identifier")
    public ResponseEntity<?> getTaskCommentById(@PathVariable Long taskCommentId) {
        return queryService.handle(new GetTaskCommentByIdQuery(taskCommentId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(TaskCommentResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("TaskComment", String.valueOf(taskCommentId))));
    }
}
