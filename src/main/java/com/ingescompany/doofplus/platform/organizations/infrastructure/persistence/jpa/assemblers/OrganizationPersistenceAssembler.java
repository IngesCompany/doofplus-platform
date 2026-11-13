package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Facility;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.FacilityPersistenceEntity;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.OrganizationPersistenceEntity;

public final class OrganizationPersistenceAssembler {

    private OrganizationPersistenceAssembler() {
    }

    public static Organization toDomainFromPersistence(OrganizationPersistenceEntity entity) {
        if (entity == null) return null;
        var facilities = entity.getFacilities().stream()
                .map(facility -> new Facility(
                        facility.getId(),
                        facility.getName(),
                        facility.getType(),
                        facility.getTimezone(),
                        facility.getOwnerName(),
                        facility.getStatus()))
                .toList();
        return Organization.reconstitute(
                entity.getId(),
                entity.getLegalName(),
                entity.getRuc(),
                entity.getRegion(),
                entity.getOwnerName(),
                entity.getStatus(),
                facilities);
    }

    public static OrganizationPersistenceEntity toPersistenceFromDomain(Organization domain) {
        if (domain == null) return null;
        var entity = new OrganizationPersistenceEntity();
        entity.setId(domain.getId());
        entity.setLegalName(domain.getLegalName());
        entity.setRuc(domain.getRuc());
        entity.setRegion(domain.getRegion());
        entity.setOwnerName(domain.getOwnerName());
        entity.setStatus(domain.getStatus());
        for (Facility facility : domain.getFacilities()) {
            var facilityEntity = new FacilityPersistenceEntity();
            facilityEntity.setId(facility.getId());
            facilityEntity.setName(facility.getName());
            facilityEntity.setType(facility.getType());
            facilityEntity.setTimezone(facility.getTimezone());
            facilityEntity.setOwnerName(facility.getOwnerName());
            facilityEntity.setStatus(facility.getStatus());
            facilityEntity.setOrganization(entity);
            entity.getFacilities().add(facilityEntity);
        }
        return entity;
    }
}
