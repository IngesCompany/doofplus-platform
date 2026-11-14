package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateBatchProgressCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductionBatchTest {

    private static ProductionBatch batch() {
        return new ProductionBatch(new CreateBatchCommand(new BatchNumber("B-26041"), 1L,
                new Quantity(new BigDecimal("120000"), "units"), " Line 02 ", "Maria Mexico"));
    }

    @Test
    void aNewBatchShouldKeepTheLineAndOwnerAndStartWithoutProgress() {
        var batch = batch();

        assertThat(batch.getLine()).isEqualTo("Line 02");
        assertThat(batch.getOwner()).isEqualTo("Maria Mexico");
        assertThat(batch.getProgress()).isZero();
    }

    @Test
    void theToneOfAnIncidentEventShouldFollowItsSeverity() {
        var batch = batch();

        batch.registerIncident(new RegisterIncidentCommand(1L, "a", IncidentSeverity.MINOR));
        batch.registerIncident(new RegisterIncidentCommand(1L, "b", IncidentSeverity.MAJOR));
        batch.registerIncident(new RegisterIncidentCommand(1L, "c", IncidentSeverity.CRITICAL));

        assertThat(batch.getEvents()).extracting("tone")
                .containsExactly(EventTone.NORMAL, EventTone.NORMAL, EventTone.WARNING, EventTone.DANGER);
    }

    @Test
    void updateProgressShouldKeepTheNewValue() {
        var batch = batch();

        batch.updateProgress(new UpdateBatchProgressCommand(1L, 68));

        assertThat(batch.getProgress()).isEqualTo(68);
    }

    @Test
    void theProgressShouldBeBetweenZeroAndOneHundred() {
        assertThatThrownBy(() -> new UpdateBatchProgressCommand(1L, 101))
                .hasMessage("manufacturing.error.batch.progress.invalid");
        assertThatThrownBy(() -> new UpdateBatchProgressCommand(1L, -1))
                .hasMessage("manufacturing.error.batch.progress.invalid");
    }
}
