package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Area;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class ProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false, length = 20)
    private Area area;

    @Column(name = "site", length = 150)
    private String site;

    @Column(name = "language", nullable = false, length = 5)
    private String language;

    @Column(name = "email_notifications", nullable = false)
    private boolean emailNotifications;

    @Column(name = "in_app_notifications", nullable = false)
    private boolean inAppNotifications;
}
