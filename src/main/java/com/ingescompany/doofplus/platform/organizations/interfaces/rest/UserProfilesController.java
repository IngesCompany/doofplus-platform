package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.UserProfileCommandService;
import com.ingescompany.doofplus.platform.organizations.application.queryservices.UserProfileQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllUserProfilesQuery;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.CreateUserProfileResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.UpdateUserProfileResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.UserProfileResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.CreateUserProfileCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.UpdateUserProfilePreferencesCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.UserProfileResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the profiles of the users of an organization.
 */
@RestController
@RequestMapping(value = "/api/v1/user-profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User Profiles", description = "User profile and preferences endpoints")
public class UserProfilesController {

    private final UserProfileCommandService commandService;
    private final UserProfileQueryService queryService;

    public UserProfilesController(UserProfileCommandService commandService, UserProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create the profile of a user")
    public ResponseEntity<?> createUserProfile(@RequestBody CreateUserProfileResource resource) {
        var command = CreateUserProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get every user profile")
    public ResponseEntity<List<UserProfileResource>> getAllUserProfiles() {
        var resources = queryService.handle(new GetAllUserProfilesQuery()).stream()
                .map(UserProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{profileId}")
    @Operation(summary = "Update the notification preferences and the language of a user profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long profileId,
                                               @RequestBody UpdateUserProfileResource resource) {
        var command = UpdateUserProfilePreferencesCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
