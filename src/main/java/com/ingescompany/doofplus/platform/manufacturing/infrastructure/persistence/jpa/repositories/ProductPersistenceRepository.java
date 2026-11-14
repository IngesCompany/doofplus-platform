package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of ProductPersistenceEntity.
 */
@Repository
public interface ProductPersistenceRepository extends JpaRepository<ProductPersistenceEntity, Long> {
    boolean existsByCode(String code);
}
