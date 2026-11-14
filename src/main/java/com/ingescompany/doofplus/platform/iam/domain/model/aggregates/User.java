package com.ingescompany.doofplus.platform.iam.domain.model.aggregates;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User aggregate root: a person of an organization who signs in to one workspace of DoofPlus.
 */
@Getter
public class User extends AbstractDomainAggregateRoot<User> {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String environment;
    private String facility;
    private String status;
    private String privilege;
    private Long organizationId;
    private String organizationName;
    private String plant;
    private String twoFactorCode;
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    /**
     * Creates a user from the sign-up command with the hashed password and the hashed two-factor code.
     *
     * @param command             the sign-up data
     * @param hashedPassword      the password hashed with BCrypt
     * @param hashedTwoFactorCode the two-factor code hashed with BCrypt, or null when it is not enrolled
     * @param roles               the roles found in the database
     */
    public User(SignUpCommand command, String hashedPassword, String hashedTwoFactorCode, List<Role> roles) {
        this();
        this.email = command.email().trim().toLowerCase();
        this.password = hashedPassword;
        this.fullName = command.fullName().trim();
        this.environment = command.environment();
        this.facility = command.facility() == null ? "" : command.facility().trim();
        this.status = command.status() == null || command.status().isBlank() ? "active" : command.status().trim();
        this.privilege = command.privilege() == null || command.privilege().isBlank() ? "none" : command.privilege().trim();
        this.organizationId = command.organizationId();
        this.organizationName = command.organizationName() == null ? "" : command.organizationName().trim();
        this.plant = command.plant() == null ? "" : command.plant().trim();
        this.twoFactorCode = hashedTwoFactorCode;
        addRoles(roles);
    }

    public static User reconstitute(Long id, String email, String password, String fullName, String environment,
                                    String facility, String status, String privilege, Long organizationId,
                                    String organizationName, String plant, String twoFactorCode, Set<Role> roles) {
        var user = new User();
        user.id = id;
        user.email = email;
        user.password = password;
        user.fullName = fullName;
        user.environment = environment;
        user.facility = facility;
        user.status = status;
        user.privilege = privilege;
        user.organizationId = organizationId;
        user.organizationName = organizationName;
        user.plant = plant;
        user.twoFactorCode = twoFactorCode;
        user.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        return user;
    }

    /**
     * Whether the user enrolled two-factor authentication.
     *
     * @return true when the user has a two-factor code
     */
    public boolean hasTwoFactor() {
        return twoFactorCode != null && !twoFactorCode.isBlank();
    }

    /**
     * Returns the initials of the full name, for example MM for María México.
     *
     * @return up to two initials
     */
    public String getInitials() {
        return Arrays.stream(fullName.trim().split("\\s+"))
                .filter(part -> !part.isBlank())
                .limit(2)
                .map(part -> part.substring(0, 1).toUpperCase())
                .collect(Collectors.joining());
    }

    /**
     * Returns the key of the main role, as the Web Application shows it (qa-specialist).
     *
     * @return the key of the first role, or an empty string
     */
    public String getRoleKey() {
        return roles.stream().map(role -> role.getName().toKey()).sorted().findFirst().orElse("");
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }
}
