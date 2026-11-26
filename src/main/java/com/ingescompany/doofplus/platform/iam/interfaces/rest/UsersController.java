package com.ingescompany.doofplus.platform.iam.interfaces.rest;

import com.ingescompany.doofplus.platform.iam.application.commandservices.UserCommandService;
import com.ingescompany.doofplus.platform.iam.application.queryservices.UserQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SignUpResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.UserResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;

/**
 * REST controller that exposes IAM user resources.
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User management endpoints")
public class UsersController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    /**
     * Handles user creation (sign-up) request.
     *
     * @param signUpResource the sign-up request body with username, password, and roles.
     * @return the created user resource with assigned roles.
     */
    @PostMapping
    @Operation(
        summary = "Create user (Sign-up)",
        description = "Creates a new user account with provided credentials and assigns specified roles."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResource.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "409",
                description = "The email is already used",
                content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<?> createUser(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var result = userCommandService.handle(signUpCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Retrieves all users.
     *
     * @return list of user resources
     * @see UserResource
     */
    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieves every user with its roles. Only for administrators.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Users retrieved successfully",
                content = @Content(schema = @Schema(implementation = UserResource.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * Retrieves a user by identifier.
     *
     * @param userId user identifier
     * @return user resource when found
     * @see UserResource
     */
    @GetMapping(value = "/{userId}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user's information by unique identifier.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "User retrieved successfully",
                content = @Content(schema = @Schema(implementation = UserResource.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> getUserById(
            @PathVariable
            @Parameter(
                description = "Unique user identifier",
                example = "1",
                required = true
            )
            Long userId
    ) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("User", userId.toString()));
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}
