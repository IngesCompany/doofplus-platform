package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.AdministrativeActivity;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.AdministrativeActivityRepository;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.AdministrativeActivityPersistenceEntity;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories.AdministrativeActivityPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdministrativeActivityRepositoryImpl implements AdministrativeActivityRepository {

    private final AdministrativeActivityPersistenceRepository persistenceRepository;

    public AdministrativeActivityRepositoryImpl(AdministrativeActivityPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public AdministrativeActivity save(AdministrativeActivity activity) {
        var entity = new AdministrativeActivityPersistenceEntity();
        entity.setId(activity.getId());
        entity.setOrganizationId(activity.getOrganizationId());
        entity.setTitle(activity.getTitle());
        entity.setActor(activity.getActor());
        entity.setOccurredAt(activity.getOccurredAt());
        return toDomain(persistenceRepository.save(entity));
    }

    @Override
    public List<AdministrativeActivity> findAll() {
        return persistenceRepository.findAllByOrderByOccurredAtDesc().stream()
                .map(AdministrativeActivityRepositoryImpl::toDomain)
                .toList();
    }

    private static AdministrativeActivity toDomain(AdministrativeActivityPersistenceEntity entity) {
        return AdministrativeActivity.reconstitute(
                entity.getId(),
                entity.getOrganizationId(),
                entity.getTitle(),
                entity.getActor(),
                entity.getOccurredAt());
    }
}
