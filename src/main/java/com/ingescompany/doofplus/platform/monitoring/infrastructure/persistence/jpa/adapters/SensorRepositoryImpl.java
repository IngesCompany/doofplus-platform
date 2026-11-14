package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.SensorRepository;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers.SensorPersistenceAssembler;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories.SensorPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing {@link SensorRepository} domain port with Spring Data JPA.
 */
@Repository
public class SensorRepositoryImpl implements SensorRepository {

    private final SensorPersistenceRepository sensorPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public SensorRepositoryImpl(SensorPersistenceRepository sensorPersistenceRepository,
                                DomainEventPublisher domainEventPublisher) {
        this.sensorPersistenceRepository = sensorPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Sensor save(Sensor sensor) {
        var persistenceEntity = SensorPersistenceAssembler.toPersistenceFromDomain(sensor);
        var savedEntity = sensorPersistenceRepository.save(persistenceEntity);
        var savedDomain = SensorPersistenceAssembler.toDomainFromPersistence(savedEntity);
        domainEventPublisher.publishAndClearEvents(sensor);
        return savedDomain;
    }

    @Override
    public Optional<Sensor> findById(Long id) {
        return sensorPersistenceRepository.findById(id)
                .map(SensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Sensor> findByCode(SensorCode code) {
        return sensorPersistenceRepository.findByCode(code)
                .map(SensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Sensor> findByDeviceId(DeviceId deviceId) {
        return sensorPersistenceRepository.findByDeviceId(deviceId)
                .map(SensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Sensor> findAll() {
        return sensorPersistenceRepository.findAll().stream()
                .map(SensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Sensor> findAllByEquipmentCode(String equipmentCode) {
        return sensorPersistenceRepository.findAllByEquipmentCode(equipmentCode).stream()
                .map(SensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByCode(SensorCode code) {
        return sensorPersistenceRepository.existsByCode(code);
    }

    @Override
    public boolean existsByDeviceId(DeviceId deviceId) {
        return sensorPersistenceRepository.existsByDeviceId(deviceId);
    }

    @Override
    public boolean existsAssignedToBatchByEquipmentCode(String equipmentCode) {
        return sensorPersistenceRepository.existsByEquipmentCodeAndAssignedBatchNot(equipmentCode, "");
    }

    @Override
    public void deleteById(Long id) {
        sensorPersistenceRepository.deleteById(id);
    }
}
