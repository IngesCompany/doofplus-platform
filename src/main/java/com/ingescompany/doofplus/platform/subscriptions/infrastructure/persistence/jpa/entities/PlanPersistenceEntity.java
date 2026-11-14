package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.PlanKeyAttributeConverter;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA entity of the {@code plans} table (database diagram of Subscriptions &amp; Payments).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plans", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"plan_key"}, name = PlanPersistenceEntity.PLAN_KEY_UNIQUE_CONSTRAINT)
})
public class PlanPersistenceEntity extends AuditableAbstractPersistenceEntity {
    public static final String PLAN_KEY_UNIQUE_CONSTRAINT = "uk_plan_key";

    @Column(nullable = false, length = 64)
    @Convert(converter = PlanKeyAttributeConverter.class)
    private PlanKey planKey;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal annualPrice;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private int iotDeviceLimit;

    @Column(nullable = false)
    private int userLimit;
}
