package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.AlertRepository;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories.AlertPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing {@link AlertRepository} domain port with Spring Data JPA.
 */
@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertPersistenceRepository alertPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public AlertRepositoryImpl(AlertPersistenceRepository alertPersistenceRepository,
                               DomainEventPublisher domainEventPublisher) {
        this.alertPersistenceRepository = alertPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Alert save(Alert alert) {
        var persistenceEntity = AlertPersistenceAssembler.toPersistenceFromDomain(alert);
        var savedEntity = alertPersistenceRepository.save(persistenceEntity);
        var savedDomain = AlertPersistenceAssembler.toDomainFromPersistence(savedEntity);
        domainEventPublisher.publishAndClearEvents(alert);
        return savedDomain;
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return alertPersistenceRepository.findById(id)
                .map(AlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Alert> findByCode(AlertCode code) {
        return alertPersistenceRepository.findByCode(code)
                .map(AlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Alert> findAll() {
        return alertPersistenceRepository.findAll().stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAllBySensorCode(String sensorCode) {
        return alertPersistenceRepository.findAllBySensorCode(sensorCode).stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAllByBatchCode(String batchCode) {
        return alertPersistenceRepository.findAllByBatchCode(batchCode).stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByCode(AlertCode code) {
        return alertPersistenceRepository.existsByCode(code);
    }

    @Override
    public void deleteById(Long id) {
        alertPersistenceRepository.deleteById(id);
    }
}
