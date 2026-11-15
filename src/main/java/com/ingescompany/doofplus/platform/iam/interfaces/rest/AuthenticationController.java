package com.ingescompany.doofplus.platform.iam.interfaces.rest;

import com.ingescompany.doofplus.platform.iam.application.commandservices.UserCommandService;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SignInResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SignUpResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.UserResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sign-in and sign-up of the users of an organization. Both endpoints are public.
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Sign-in with two-factor authentication and sign-up")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in",
            description = "Checks the email and the password. When the user enrolled two-factor authentication and "
                    + "no code is sent, answers 401 with details two-factor-required; send the request again with "
                    + "the code to receive the user and its JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Signed in",
                    content = @Content(schema = @Schema(implementation = AuthenticatedUserResource.class))),
            @ApiResponse(responseCode = "401",
                    description = "details: invalid-credentials, two-factor-required or invalid-two-factor-code",
                    content = @Content(mediaType = "application/problem+json"))
    })
    public ResponseEntity<?> signIn(@RequestBody SignInResource resource) {
        var result = userCommandService.handle(SignInCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                auth -> AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(auth.getLeft(), auth.getRight()),
                HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up", description = "Registers a user with its role, workspace and organization.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "409", description = "The email is already used",
                    content = @Content(mediaType = "application/problem+json"))
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpResource resource) {
        var result = userCommandService.handle(SignUpCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                UserResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }
}
