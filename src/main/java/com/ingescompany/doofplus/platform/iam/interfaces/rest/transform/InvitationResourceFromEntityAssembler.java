package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.InvitationResource;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Maps an invitation to its resource; sentAt travels in UTC (ISO 8601 with Z).
 */
public final class InvitationResourceFromEntityAssembler {

    private InvitationResourceFromEntityAssembler() {
    }

    public static InvitationResource toResourceFromEntity(Invitation invitation) {
        var sentAt = invitation.getSentAt() == null ? null
                : DateTimeFormatter.ISO_INSTANT.format(invitation.getSentAt().toInstant(ZoneOffset.UTC));
        return new InvitationResource(invitation.getId(), invitation.getOrganizationId(), invitation.getFullName(),
                invitation.getEmail(), invitation.getRole().toKey(), invitation.getFacility(), invitation.getNote(),
                invitation.getStatus().name(), sentAt);
    }
}
