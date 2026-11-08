package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.EquipmentRepository;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing {@link EquipmentRepository} domain port with Spring Data JPA.
 */
@Repository
public class EquipmentRepositoryImpl implements EquipmentRepository {

    private final EquipmentPersistenceRepository equipmentPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public EquipmentRepositoryImpl(EquipmentPersistenceRepository equipmentPersistenceRepository,
                                   DomainEventPublisher domainEventPublisher) {
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Equipment save(Equipment equipment) {
        var persistenceEntity = EquipmentPersistenceAssembler.toPersistenceFromDomain(equipment);
        var savedEntity = equipmentPersistenceRepository.save(persistenceEntity);
        var savedDomain = EquipmentPersistenceAssembler.toDomainFromPersistence(savedEntity);
        domainEventPublisher.publishAndClearEvents(equipment);
        return savedDomain;
    }

    @Override
    public Optional<Equipment> findById(Long id) {
        return equipmentPersistenceRepository.findById(id)
                .map(EquipmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Equipment> findByCode(EquipmentCode code) {
        return equipmentPersistenceRepository.findByCode(code)
                .map(EquipmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentPersistenceRepository.findAll().stream()
                .map(EquipmentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByCode(EquipmentCode code) {
        return equipmentPersistenceRepository.existsByCode(code);
    }

    @Override
    public void deleteById(Long id) {
        equipmentPersistenceRepository.deleteById(id);
    }
}
