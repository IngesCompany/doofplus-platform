package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Row of an invitation.
 */
@Entity
@Table(name = "iam_invitations")
@Getter
@Setter
@NoArgsConstructor
public class InvitationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "facility", nullable = false, length = 100)
    private String facility;

    @Column(name = "role", nullable = false, length = 40)
    private String role;

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "note", nullable = false, length = 500)
    private String note;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
}
