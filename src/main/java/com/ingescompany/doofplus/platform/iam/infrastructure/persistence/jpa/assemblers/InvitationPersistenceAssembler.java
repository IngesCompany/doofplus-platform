package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.InvitationStatus;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.entities.InvitationPersistenceEntity;

/**
 * Maps invitations to and from their rows.
 */
public final class InvitationPersistenceAssembler {

    private InvitationPersistenceAssembler() {
    }

    public static Invitation toDomainFromPersistence(InvitationPersistenceEntity entity) {
        if (entity == null) return null;
        return Invitation.reconstitute(entity.getId(), entity.getOrganizationId(), entity.getFacility(),
                Roles.valueOf(entity.getRole()), entity.getFullName(), entity.getEmail(), entity.getNote(),
                InvitationStatus.valueOf(entity.getStatus()), entity.getSentAt());
    }

    public static InvitationPersistenceEntity toPersistenceFromDomain(Invitation invitation) {
        var entity = new InvitationPersistenceEntity();
        entity.setId(invitation.getId());
        entity.setOrganizationId(invitation.getOrganizationId());
        entity.setFacility(invitation.getFacility());
        entity.setRole(invitation.getRole().name());
        entity.setFullName(invitation.getFullName());
        entity.setEmail(invitation.getEmail());
        entity.setNote(invitation.getNote());
        entity.setStatus(invitation.getStatus().name());
        entity.setSentAt(invitation.getSentAt());
        return entity;
    }
}
