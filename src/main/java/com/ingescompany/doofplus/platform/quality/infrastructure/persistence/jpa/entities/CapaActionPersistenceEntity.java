package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the capa_actions table, as in db-06-quality.
 */
@Entity
@Table(name = "capa_actions")
@Getter
@Setter
@NoArgsConstructor
public class CapaActionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "capa_code", length = 30, nullable = false)
    private String capaCode;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 24, nullable = false)
    private CapaType type;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "due_at")
    private LocalDate dueAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private ActionStatus status;

    @Column(name = "evidence", length = 150, nullable = false)
    private String evidence;

    @Column(name = "evidence_attached", nullable = false)
    private Boolean evidenceAttached;
}
