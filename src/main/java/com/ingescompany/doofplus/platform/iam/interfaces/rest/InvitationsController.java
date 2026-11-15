package com.ingescompany.doofplus.platform.iam.interfaces.rest;

import com.ingescompany.doofplus.platform.iam.application.commandservices.InvitationCommandService;
import com.ingescompany.doofplus.platform.iam.application.queryservices.InvitationQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllInvitationsQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetInvitationByIdQuery;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.InvitationResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SendInvitationResource;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.InvitationResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.transform.SendInvitationCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Invitations of new members (US55). Only for administrators.
 */
@RestController
@RequestMapping(value = "/api/v1/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invitations", description = "Invitations of new members of an organization")
public class InvitationsController {

    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;

    public InvitationsController(InvitationCommandService invitationCommandService,
                                 InvitationQueryService invitationQueryService) {
        this.invitationCommandService = invitationCommandService;
        this.invitationQueryService = invitationQueryService;
    }

    @PostMapping
    @Operation(summary = "Send an invitation", security = @SecurityRequirement(name = "bearerAuth"),
            description = "Invites a new member with a role and a facility; the invitation starts PENDING. "
                    + "An email of a user who is no longer invited, or of a pending invitation, answers 409.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Invitation sent"),
            @ApiResponse(responseCode = "400", description = "Missing name, email or facility, or a role that is not valid"),
            @ApiResponse(responseCode = "401", description = "JWT token required or invalid"),
            @ApiResponse(responseCode = "403", description = "Only for administrators"),
            @ApiResponse(responseCode = "409", description = "The email is already used")
    })
    public ResponseEntity<?> sendInvitation(@RequestBody SendInvitationResource resource) {
        var result = invitationCommandService.handle(SendInvitationCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                InvitationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get the invitations", security = @SecurityRequirement(name = "bearerAuth"),
            description = "Gets the invitations; with organizationId, only the ones of that organization.")
    public ResponseEntity<List<InvitationResource>> getInvitations(@RequestParam(required = false) Long organizationId) {
        var invitations = invitationQueryService.handle(new GetAllInvitationsQuery(organizationId));
        return ResponseEntity.ok(invitations.stream().map(InvitationResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @GetMapping("/{invitationId}")
    @Operation(summary = "Get an invitation by its id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invitation found"),
            @ApiResponse(responseCode = "404", description = "Invitation not found")
    })
    public ResponseEntity<InvitationResource> getInvitationById(@PathVariable Long invitationId) {
        return invitationQueryService.handle(new GetInvitationByIdQuery(invitationId))
                .map(InvitationResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
