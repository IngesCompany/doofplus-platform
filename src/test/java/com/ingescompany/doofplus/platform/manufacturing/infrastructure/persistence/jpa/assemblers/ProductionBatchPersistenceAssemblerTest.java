package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductionBatchPersistenceAssemblerTest {

    private static ProductionBatch batch() {
        return new ProductionBatch(new CreateBatchCommand(new BatchNumber("B-26041"), 1L,
                new Quantity(new BigDecimal("120000"), "units")));
    }

    @Test
    void theIncidentsEventsAndConsumptionsShouldSurviveTheRoundTrip() {
        var batch = batch();
        batch.registerIncident(new RegisterIncidentCommand(1L, "Temperature excursion", IncidentSeverity.MAJOR));
        batch.consumeMaterial(7L, new Quantity(new BigDecimal("60.5"), "kg"));

        var entity = ProductionBatchPersistenceAssembler.toPersistenceFromDomain(batch);
        var rebuilt = ProductionBatchPersistenceAssembler.toDomainFromPersistence(entity);

        assertThat(entity.getIncidents()).hasSize(1);
        assertThat(entity.getConsumptions()).hasSize(1);
        assertThat(entity.getEvents()).hasSize(3);
        assertThat(rebuilt.getIncidents().getFirst().getDescription()).isEqualTo("Temperature excursion");
        assertThat(rebuilt.getIncidents().getFirst().getRecordedAt()).isNotNull();
        assertThat(rebuilt.getConsumptions().getFirst().getRawMaterialLotId()).isEqualTo(7L);
        assertThat(rebuilt.getConsumptions().getFirst().getConsumedQuantity().value()).isEqualByComparingTo("60.5");
        assertThat(rebuilt.getConsumptions().getFirst().getConsumedAt()).isNotNull();
        assertThat(rebuilt.getEvents()).extracting("type")
                .containsExactly("BATCH_CREATED", "INCIDENT_REGISTERED", "MATERIAL_CONSUMED");
    }

    @Test
    void aNewBatchShouldOnlyHaveItsCreationEvent() {
        var rebuilt = ProductionBatchPersistenceAssembler.toDomainFromPersistence(
                ProductionBatchPersistenceAssembler.toPersistenceFromDomain(batch()));

        assertThat(rebuilt.getIncidents()).isEmpty();
        assertThat(rebuilt.getConsumptions()).isEmpty();
        assertThat(rebuilt.getEvents()).extracting("type").containsExactly("BATCH_CREATED");
    }
}
