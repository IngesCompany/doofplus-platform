package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Plant;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.OrganizationPersistenceEntity;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.PlantPersistenceEntity;

public final class OrganizationPersistenceAssembler {

    private OrganizationPersistenceAssembler() {
    }

    public static Organization toDomainFromPersistence(OrganizationPersistenceEntity entity) {
        if (entity == null) return null;
        var plants = entity.getPlants().stream()
                .map(plant -> new Plant(plant.getId(), plant.getName(), plant.getAddress()))
                .toList();
        return Organization.reconstitute(
                entity.getId(),
                entity.getLegalName(),
                entity.getRuc(),
                entity.getStatus(),
                plants);
    }

    public static OrganizationPersistenceEntity toPersistenceFromDomain(Organization domain) {
        if (domain == null) return null;
        var entity = new OrganizationPersistenceEntity();
        entity.setId(domain.getId());
        entity.setLegalName(domain.getLegalName());
        entity.setRuc(domain.getRuc());
        entity.setStatus(domain.getStatus());
        for (Plant plant : domain.getPlants()) {
            var plantEntity = new PlantPersistenceEntity();
            plantEntity.setId(plant.getId());
            plantEntity.setName(plant.getName());
            plantEntity.setAddress(plant.getAddress());
            plantEntity.setOrganization(entity);
            entity.getPlants().add(plantEntity);
        }
        return entity;
    }
}
