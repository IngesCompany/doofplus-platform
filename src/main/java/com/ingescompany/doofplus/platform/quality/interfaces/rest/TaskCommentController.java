package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TaskCommentCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.TaskCommentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentsQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTaskCommentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TaskCommentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateTaskCommentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.TaskCommentResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/task-comments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Task Comments", description = "Task comment management endpoints")
public class TaskCommentController {

    private final TaskCommentCommandService commandService;
    private final TaskCommentQueryService queryService;

    public TaskCommentController(
            TaskCommentCommandService commandService,
            TaskCommentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a task comment")
    public ResponseEntity<?> createComment(
            @RequestBody CreateTaskCommentResource resource) {

        var command =
                CreateTaskCommentCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TaskCommentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/by-task/{taskId}")
    @Operation(summary = "Get comments by task ID")
    public ResponseEntity<List<TaskCommentResource>> getCommentsByTaskId(
            @PathVariable Long taskId) {

        List<TaskCommentResource> comments =
                queryService.handle(new GetTaskCommentsQuery(taskId))
                        .stream()
                        .map(TaskCommentResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();

        return ResponseEntity.ok(comments);
    }
}