package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaActionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the capa_actions table.
 */
@Repository
public interface CapaActionPersistenceRepository extends JpaRepository<CapaActionPersistenceEntity, Long> {

    /**
     * Lists the rows with the given capa code.
     *
     * @param capaCode the capa code
     * @return the rows
     */
    List<CapaActionPersistenceEntity> findByCapaCode(String capaCode);
}
