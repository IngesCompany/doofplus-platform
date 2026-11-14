package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.MaterialCategory;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.RawMaterialLotStatus;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.SamplingStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA entity of the {@code raw_material_lots} table.
 */
@Entity
@Table(name = "raw_material_lots")
@Getter
@Setter
@NoArgsConstructor
public class RawMaterialLotPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "receipt_code", length = 30)
    private String receiptCode;

    @Column(name = "material", nullable = false, length = 150)
    private String material;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private MaterialCategory category;

    @Column(name = "supplier", length = 150)
    private String supplier;

    @Column(name = "supplier_lot", length = 50)
    private String supplierLot;

    @Column(name = "quantity", length = 100)
    private String quantity;

    @Column(name = "storage_location", length = 100)
    private String storageLocation;

    @Column(name = "certificate", length = 255)
    private String certificate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RawMaterialLotStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "sampling_status", nullable = false, length = 20)
    private SamplingStatus samplingStatus;

    @Column(name = "packaging_verified", nullable = false)
    private boolean packagingVerified;

    @Column(name = "identity_verified", nullable = false)
    private boolean identityVerified;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "allocated_batch", length = 30)
    private String allocatedBatch;
}
