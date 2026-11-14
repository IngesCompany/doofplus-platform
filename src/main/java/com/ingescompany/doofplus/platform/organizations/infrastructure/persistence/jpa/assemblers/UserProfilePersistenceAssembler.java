package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.NotificationPreferences;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.PersonName;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.UserProfilePersistenceEntity;

public final class UserProfilePersistenceAssembler {

    private UserProfilePersistenceAssembler() {
    }

    public static UserProfile toDomainFromPersistence(UserProfilePersistenceEntity entity) {
        if (entity == null) return null;
        return UserProfile.reconstitute(
                entity.getId(),
                entity.getUserId(),
                entity.getOrganizationId(),
                new PersonName(entity.getFirstName(), entity.getLastName()),
                entity.getEmail(),
                entity.getArea(),
                entity.getSite(),
                entity.getLanguage(),
                new NotificationPreferences(entity.isEmailNotifications(), entity.isInAppNotifications()));
    }

    public static UserProfilePersistenceEntity toPersistenceFromDomain(UserProfile domain) {
        if (domain == null) return null;
        var entity = new UserProfilePersistenceEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setOrganizationId(domain.getOrganizationId());
        entity.setFirstName(domain.getName().firstName());
        entity.setLastName(domain.getName().lastName());
        entity.setEmail(domain.getEmail());
        entity.setArea(domain.getArea());
        entity.setSite(domain.getSite());
        entity.setLanguage(domain.getLanguage());
        entity.setEmailNotifications(domain.getPreferences().emailEnabled());
        entity.setInAppNotifications(domain.getPreferences().inAppEnabled());
        return entity;
    }
}
