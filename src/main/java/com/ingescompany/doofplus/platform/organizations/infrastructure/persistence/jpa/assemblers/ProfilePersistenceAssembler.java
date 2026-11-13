package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.NotificationPreferences;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.PersonName;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

public final class ProfilePersistenceAssembler {

    private ProfilePersistenceAssembler() {
    }

    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        if (entity == null) return null;
        return Profile.reconstitute(
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

    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile domain) {
        if (domain == null) return null;
        var entity = new ProfilePersistenceEntity();
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
