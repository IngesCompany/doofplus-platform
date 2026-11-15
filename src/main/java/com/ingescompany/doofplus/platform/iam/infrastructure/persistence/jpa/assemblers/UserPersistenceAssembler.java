package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Converts users between the domain and the JPA entity.
 */
public final class UserPersistenceAssembler {

    private UserPersistenceAssembler() {
    }

    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        if (entity == null) return null;
        var roles = entity.getRoles() == null
                ? new HashSet<Role>()
                : entity.getRoles().stream()
                .map(RolePersistenceAssembler::toDomainFromPersistence)
                .collect(Collectors.toSet());
        return User.reconstitute(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getFullName(),
                entity.getEnvironment(), entity.getFacility(), entity.getStatus(), entity.getPrivilege(),
                entity.getOrganizationId(), entity.getOrganizationName(), entity.getPlant(),
                entity.getTwoFactorCode(), roles);
    }

    public static UserPersistenceEntity toPersistenceFromDomain(User user) {
        if (user == null) return null;
        var entity = new UserPersistenceEntity();
        // Only set the id when the user is being updated; new users let JPA generate it.
        if (user.getId() != null) {
            entity.setId(user.getId());
        }
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setFullName(user.getFullName());
        entity.setEnvironment(user.getEnvironment());
        entity.setFacility(user.getFacility());
        entity.setStatus(user.getStatus());
        entity.setPrivilege(user.getPrivilege());
        entity.setOrganizationId(user.getOrganizationId());
        entity.setOrganizationName(user.getOrganizationName());
        entity.setPlant(user.getPlant());
        entity.setTwoFactorCode(user.getTwoFactorCode());
        entity.setRoles(user.getRoles() == null
                ? new HashSet<>()
                : user.getRoles().stream()
                .map(RolePersistenceAssembler::toPersistenceFromDomain)
                .collect(Collectors.toSet()));
        return entity;
    }
}
