package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductRepository;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers.CatalogPersistenceAssembler;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories.ProductPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link ProductRepository}.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductPersistenceRepository persistenceRepository;

    public ProductRepositoryImpl(ProductPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Product save(Product product) {
        var saved = persistenceRepository.saveAndFlush(CatalogPersistenceAssembler.toPersistence(product));
        return CatalogPersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return persistenceRepository.findById(id).map(CatalogPersistenceAssembler::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return persistenceRepository.findAll().stream().map(CatalogPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return persistenceRepository.existsByCode(code);
    }
}
