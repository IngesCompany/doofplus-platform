package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.AlertCodeAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA persistence entity for Alert.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "alerts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "uk_alert_code")
})
public class AlertPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @Convert(converter = AlertCodeAttributeConverter.class)
    private AlertCode code;

    @Column(nullable = false)
    private String sensorCode;

    @Column(nullable = false)
    private String equipmentCode;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String detail;

    @Column
    private String batchCode;

    @Column
    private String acknowledgedBy;

    @Column
    private String acknowledgedAt;

    @Column(length = 1000)
    private String responseNote;

    @Column
    private String linkedRecord;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
}
