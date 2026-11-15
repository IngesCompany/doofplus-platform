package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the evidence table, as in db-06-quality.
 */
@Entity
@Table(name = "evidence")
@Getter
@Setter
@NoArgsConstructor
public class EvidencePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "record_code", length = 60, nullable = false)
    private String recordCode;

    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @Column(name = "detail", length = 255, nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private EvidenceStatus status;
}
