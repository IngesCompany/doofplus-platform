package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.ReadingRepository;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers.ReadingPersistenceAssembler;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.repositories.ReadingPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing {@link ReadingRepository} domain port with Spring Data JPA.
 */
@Repository
public class ReadingRepositoryImpl implements ReadingRepository {

    private final ReadingPersistenceRepository readingPersistenceRepository;

    public ReadingRepositoryImpl(ReadingPersistenceRepository readingPersistenceRepository) {
        this.readingPersistenceRepository = readingPersistenceRepository;
    }

    @Override
    public Reading save(Reading reading) {
        var persistenceEntity = ReadingPersistenceAssembler.toPersistenceFromDomain(reading);
        var savedEntity = readingPersistenceRepository.save(persistenceEntity);
        return ReadingPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Reading> findById(Long id) {
        return readingPersistenceRepository.findById(id)
                .map(ReadingPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Reading> findAll() {
        return readingPersistenceRepository.findAll().stream()
                .map(ReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Reading> findAllBySensorCode(String sensorCode) {
        return readingPersistenceRepository.findAllBySensorCode(sensorCode).stream()
                .map(ReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Reading> findAllByBatchCode(String batchCode) {
        return readingPersistenceRepository.findAllByBatchCode(batchCode).stream()
                .map(ReadingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        readingPersistenceRepository.deleteById(id);
    }
}
