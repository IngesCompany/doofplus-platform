package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ReleaseBatchCommand;
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
    void theBatchShouldGoThroughTheStatusesOfTheWebApp() {
        var batch = batch();
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.PLANNED);

        batch.start();
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.IN_PROGRESS);

        batch.registerIncident(new RegisterIncidentCommand(1L, "Seal failure", IncidentSeverity.CRITICAL));
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.ON_HOLD);

        batch.resume();
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.IN_PROGRESS);

        batch.close(new CloseBatchCommand(1L, new Quantity(new BigDecimal("118000"), "units")));
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.RELEASE_REQUESTED);
        assertThat(batch.getProgress()).isEqualTo(100);
        assertThat(batch.getFinishedAt()).isNotNull();

        batch.release(new ReleaseBatchCommand(1L, "Lucia Paredes"));
        assertThat(batch.getStatus()).isEqualTo(BatchStatus.RELEASED);
        assertThat(batch.getEvents().getLast().getDescription()).contains("Lucia Paredes");
    }

    @Test
    void aStatusChangeThatIsNotAllowedShouldFail() {
        var batch = batch();

        assertThatThrownBy(batch::resume).hasMessage("manufacturing.error.batch.cannotResume");
        assertThatThrownBy(() -> batch.close(new CloseBatchCommand(1L, new Quantity(BigDecimal.ONE, "units"))))
                .hasMessage("manufacturing.error.batch.cannotClose");
        assertThatThrownBy(() -> batch.release(new ReleaseBatchCommand(1L, "Lucia Paredes")))
                .hasMessage("manufacturing.error.batch.cannotRelease");
        batch.start();
        assertThatThrownBy(batch::start).hasMessage("manufacturing.error.batch.cannotStart");
    }

    @Test
    void releaseShouldRequireWhoReleasesTheBatch() {
        assertThatThrownBy(() -> new ReleaseBatchCommand(1L, " "))
                .hasMessage("manufacturing.error.batch.releasedBy.notBlank");
    }

    @Test
    void theProgressShouldBeBetweenZeroAndOneHundred() {
        assertThatThrownBy(() -> new UpdateBatchProgressCommand(1L, 101))
                .hasMessage("manufacturing.error.batch.progress.invalid");
        assertThatThrownBy(() -> new UpdateBatchProgressCommand(1L, -1))
                .hasMessage("manufacturing.error.batch.progress.invalid");
    }
}
