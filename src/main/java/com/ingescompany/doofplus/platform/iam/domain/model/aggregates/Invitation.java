package com.ingescompany.doofplus.platform.iam.domain.model.aggregates;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.InvitationStatus;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Invitation of a new member of an organization (US55, class-01-iam). The account of the member is created when the
 * invitation is accepted.
 */
@Getter
public class Invitation extends AbstractDomainAggregateRoot<Invitation> {

    private Long id;
    private Long organizationId;
    private String facility;
    private Roles role;
    private String fullName;
    private String email;
    private String note;
    private InvitationStatus status;
    private LocalDateTime sentAt;

    private Invitation() {
    }

    /**
     * Creates a pending invitation with its data trimmed and the email in lower case.
     *
     * @param command data of the invitation
     * @param role    role already validated
     */
    public Invitation(SendInvitationCommand command, Roles role) {
        this.organizationId = command.organizationId();
        this.facility = command.facility().trim();
        this.role = role;
        this.fullName = command.fullName().trim();
        this.email = command.email().trim().toLowerCase();
        this.note = command.note() == null ? "" : command.note().trim();
        this.status = InvitationStatus.PENDING;
        this.sentAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     * Rebuilds an invitation read from the database.
     *
     * @return the invitation
     */
    public static Invitation reconstitute(Long id, Long organizationId, String facility, Roles role, String fullName,
                                          String email, String note, InvitationStatus status, LocalDateTime sentAt) {
        var invitation = new Invitation();
        invitation.id = id;
        invitation.organizationId = organizationId;
        invitation.facility = facility;
        invitation.role = role;
        invitation.fullName = fullName;
        invitation.email = email;
        invitation.note = note == null ? "" : note;
        invitation.status = status;
        invitation.sentAt = sentAt;
        return invitation;
    }

    /**
     * Accepts a pending invitation.
     *
     * @return true when it was pending
     */
    public boolean accept() {
        if (status != InvitationStatus.PENDING) return false;
        status = InvitationStatus.ACCEPTED;
        return true;
    }

    /**
     * Expires a pending invitation.
     *
     * @return true when it was pending
     */
    public boolean expire() {
        if (status != InvitationStatus.PENDING) return false;
        status = InvitationStatus.EXPIRED;
        return true;
    }
}
