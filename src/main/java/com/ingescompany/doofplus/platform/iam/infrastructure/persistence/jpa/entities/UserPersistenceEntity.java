package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity of the users. The password and the two-factor code are stored hashed with BCrypt.
 */
@Entity
@Table(name = "iam_users")
@Getter
@Setter
@NoArgsConstructor
public class UserPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "environment", nullable = false, length = 20)
    private String environment;

    @Column(name = "facility", nullable = false, length = 100)
    private String facility;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "privilege", nullable = false, length = 30)
    private String privilege;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "organization_name", nullable = false, length = 150)
    private String organizationName;

    @Column(name = "plant", nullable = false, length = 100)
    private String plant;

    @Column(name = "two_factor_code", length = 120)
    private String twoFactorCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "iam_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolePersistenceEntity> roles = new HashSet<>();
}
