package com.ingescompany.doofplus.platform.quality;

import com.ingescompany.doofplus.platform.quality.application.internal.commandservices.EvidenceCommandServiceImpl;
import com.ingescompany.doofplus.platform.quality.application.internal.queryservices.DeviationTrendQueryServiceImpl;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditEventCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.DeleteEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationTrendsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationTrend;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TaskStatus;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.QualityValueParser;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Rules of contratos/quality-api.json in the Quality and Compliance bounded context.
 */
class QualityContractTest {

    private static Deviation deviation(String code, String rootCause, LocalDateTime reportedAt) {
        return new Deviation(new CreateDeviationCommand(code, "Temperature excursion", "B-26041", null, null, null,
                null, null, null, null, null, rootCause, null, null, reportedAt, null, null));
    }

    @Test
    void enumsAreAcceptedInKebabPascalOrUpperSnakeCase() {
        assertThat(QualityValueParser.toEnum(DocumentStatus.class, "in-review", "key")).isEqualTo(DocumentStatus.IN_REVIEW);
        assertThat(QualityValueParser.toEnum(DocumentStatus.class, "InReview", "key")).isEqualTo(DocumentStatus.IN_REVIEW);
        assertThat(QualityValueParser.toEnum(DocumentStatus.class, "IN_REVIEW", "key")).isEqualTo(DocumentStatus.IN_REVIEW);
        assertThat(QualityValueParser.toEnum(TaskStatus.class, "to-do", "key")).isEqualTo(TaskStatus.TO_DO);
        assertThat(QualityValueParser.toEnum(TaskStatus.class, null, "key")).isNull();
        assertThatThrownBy(() -> QualityValueParser.toEnum(Classification.class, "huge", "quality.key"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("quality.key");
    }

    @Test
    void dateTimesTravelInUtc() {
        var parsed = QualityValueParser.toDateTime("2026-10-07T05:32:00-05:00", "key");
        assertThat(parsed).isEqualTo(LocalDateTime.of(2026, 10, 7, 10, 32));
        assertThat(QualityValueParser.fromDateTime(parsed)).isEqualTo("2026-10-07T10:32:00Z");
    }

    @Test
    void aDeviationNeedsCodeAndTitleAndTakesTheDefaults() {
        assertThatThrownBy(() -> deviation(" ", null, null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("quality.deviation.error.code.notBlank");
        var created = deviation(" DEV-26017 ", null, null);
        assertThat(created.getCode()).isEqualTo("DEV-26017");
        assertThat(created.getClassification()).isEqualTo(Classification.MINOR);
        assertThat(created.getStatus()).isEqualTo(DeviationStatus.OPEN);
        assertThat(created.getSeverity()).isEqualTo(1);
        assertThat(created.getRootCause()).isEmpty();
    }

    @Test
    void aCapaActionWithoutTypeIsPreventiveWhenItsCodeStartsWithPa() {
        var preventive = new CapaAction(new CreateCapaActionCommand("CAPA-26008", "pa-01", null, "Train operators",
                null, null, null, null, null));
        var corrective = new CapaAction(new CreateCapaActionCommand("CAPA-26008", "CA-01", null, "Fix chamber",
                null, null, null, null, null));
        assertThat(preventive.getType()).isEqualTo(CapaType.PREVENTIVE);
        assertThat(corrective.getType()).isEqualTo(CapaType.CORRECTIVE);
        assertThat(corrective.getStatus()).isEqualTo(ActionStatus.OPEN);
        assertThat(corrective.getEvidenceAttached()).isFalse();
    }

    @Test
    void anAuditEventWithoutTimeTakesTheTimeOfTheRequest() {
        var event = new AuditEvent(new CreateAuditEventCommand("EVT-1", null, "María México", null, "Login", null,
                null, null, null, null, null));
        assertThat(event.getOccurredAt()).isNotNull();
    }

    @Test
    void reviewedEvidenceCannotBeRemoved() {
        IEvidenceRepository repository = mock(IEvidenceRepository.class);
        var reviewed = Evidence.reconstitute(1L, "DEV-26017", "coa.pdf", "", EvidenceStatus.REVIEWED);
        var attached = Evidence.reconstitute(2L, "DEV-26017", "photo.jpg", "", EvidenceStatus.ATTACHED);
        when(repository.findById(1L)).thenReturn(Optional.of(reviewed));
        when(repository.findById(2L)).thenReturn(Optional.of(attached));
        when(repository.findById(3L)).thenReturn(Optional.empty());
        var service = new EvidenceCommandServiceImpl(repository);

        var conflict = service.handle(new DeleteEvidenceCommand(1L));
        assertThat(conflict).isInstanceOf(Result.Failure.class);
        assertThat(((Result.Failure<Long, ApplicationError>) conflict).error().code()).isEqualTo("EVIDENCE_CONFLICT");
        verify(repository, never()).deleteById(1L);

        assertThat(service.handle(new DeleteEvidenceCommand(2L))).isInstanceOf(Result.Success.class);
        verify(repository).deleteById(2L);

        var missing = service.handle(new DeleteEvidenceCommand(3L));
        assertThat(((Result.Failure<Long, ApplicationError>) missing).error().code()).isEqualTo("EVIDENCE_NOT_FOUND");
    }

    @Test
    void theTrendCountsDeviationsByMonthAndRootCause() {
        IDeviationRepository repository = mock(IDeviationRepository.class);
        when(repository.findAll()).thenReturn(List.of(
                deviation("DEV-3", "Procedure not followed", LocalDateTime.of(2026, 10, 2, 8, 0)),
                deviation("DEV-1", "Equipment failure", LocalDateTime.of(2026, 9, 20, 8, 0)),
                deviation("DEV-2", "Equipment failure", LocalDateTime.of(2026, 10, 5, 8, 0)),
                deviation("DEV-4", "Equipment failure", LocalDateTime.of(2026, 10, 9, 8, 0)),
                deviation("DEV-5", "", LocalDateTime.of(2026, 10, 9, 8, 0)),
                deviation("DEV-6", "Equipment failure", null)));

        var trend = new DeviationTrendQueryServiceImpl(repository).handle(new GetDeviationTrendsQuery());

        assertThat(trend).containsExactly(
                new DeviationTrend("2026-09", "Equipment failure", 1),
                new DeviationTrend("2026-10", "Equipment failure", 2),
                new DeviationTrend("2026-10", "Procedure not followed", 1));
    }
}
