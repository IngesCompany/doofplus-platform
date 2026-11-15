package com.ingescompany.doofplus.platform.iam.domain.model.valueobjects;

/**
 * Roles of DoofPlus. Each one matches a least-privilege profile of the Web Application.
 */
public enum Roles {
    ROLE_ADMINISTRATOR,
    ROLE_QA_SPECIALIST,
    ROLE_PRODUCTION_SUPERVISOR,
    ROLE_QUALITY_MANAGER,
    ROLE_INTERNAL_AUDITOR;

    /**
     * Converts the key that the Web Application uses (qa-specialist) or the role name (ROLE_QA_SPECIALIST).
     *
     * @param value the key or the name of the role
     * @return the role
     * @throws IllegalArgumentException when the value is not a role
     */
    public static Roles fromKey(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Role is required");
        var name = value.trim().toUpperCase().replace('-', '_');
        try {
            return Roles.valueOf(name.startsWith("ROLE_") ? name : "ROLE_" + name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role is not valid: " + value);
        }
    }

    /**
     * Returns the key that the Web Application uses, for example qa-specialist.
     *
     * @return the key of the role
     */
    public String toKey() {
        return name().substring("ROLE_".length()).toLowerCase().replace('_', '-');
    }
}
