package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * Deviation of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class Deviation extends AbstractDomainAggregateRoot<Deviation> {

    /**
     * The identifier; null until the deviation is saved.
     */
    private Long id;

    /**
     * The code.
     */
    private String code;

    /**
     * The title.
     */
    private String title;

    /**
     * The batch code.
     */
    private String batchCode;

    /**
     * The order code.
     */
    private String orderCode;

    /**
     * The summary.
     */
    private String summary;

    /**
     * The classification.
     */
    private Classification classification;

    /**
     * The containment.
     */
    private String containment;

    /**
     * The severity.
     */
    private Integer severity;

    /**
     * The likelihood.
     */
    private Integer likelihood;

    /**
     * The detectability.
     */
    private Integer detectability;

    /**
     * The status.
     */
    private DeviationStatus status;

    /**
     * The root cause.
     */
    private String rootCause;

    /**
     * The capa code.
     */
    private String capaCode;

    /**
     * The reported by.
     */
    private String reportedBy;

    /**
     * The reported at.
     */
    private LocalDateTime reportedAt;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The due at.
     */
    private LocalDateTime dueAt;

    private Deviation() {
    }

    /**
     * Creates the deviation from its command.
     *
     * @param command the data of the deviation
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public Deviation(CreateDeviationCommand command) {
        apply(command.code(), command.title(), command.batchCode(), command.orderCode(), command.summary(), command.classification(), command.containment(), command.severity(), command.likelihood(), command.detectability(), command.status(), command.rootCause(), command.capaCode(), command.reportedBy(), command.reportedAt(), command.owner(), command.dueAt());
    }

    /**
     * Rebuilds a saved deviation without validating it again.
     *
     * @return the deviation
     */
    public static Deviation reconstitute(Long id, String code, String title, String batchCode, String orderCode, String summary, Classification classification, String containment, Integer severity, Integer likelihood, Integer detectability, DeviationStatus status, String rootCause, String capaCode, String reportedBy, LocalDateTime reportedAt, String owner, LocalDateTime dueAt) {
        var deviation = new Deviation();
        deviation.id = id;
        deviation.code = code;
        deviation.title = title;
        deviation.batchCode = batchCode;
        deviation.orderCode = orderCode;
        deviation.summary = summary;
        deviation.classification = classification;
        deviation.containment = containment;
        deviation.severity = severity;
        deviation.likelihood = likelihood;
        deviation.detectability = detectability;
        deviation.status = status;
        deviation.rootCause = rootCause;
        deviation.capaCode = capaCode;
        deviation.reportedBy = reportedBy;
        deviation.reportedAt = reportedAt;
        deviation.owner = owner;
        deviation.dueAt = dueAt;
        return deviation;
    }

    /**
     * Replaces the data of the deviation with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateDeviationCommand command) {
        apply(command.code(), command.title(), command.batchCode(), command.orderCode(), command.summary(), command.classification(), command.containment(), command.severity(), command.likelihood(), command.detectability(), command.status(), command.rootCause(), command.capaCode(), command.reportedBy(), command.reportedAt(), command.owner(), command.dueAt());
    }

    private void apply(String code, String title, String batchCode, String orderCode, String summary, Classification classification, String containment, Integer severity, Integer likelihood, Integer detectability, DeviationStatus status, String rootCause, String capaCode, String reportedBy, LocalDateTime reportedAt, String owner, LocalDateTime dueAt) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.deviation.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.deviation.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.batchCode = batchCode == null ? "" : batchCode.trim();
        this.orderCode = orderCode == null ? "" : orderCode.trim();
        this.summary = summary == null ? "" : summary.trim();
        this.classification = classification != null ? classification : Classification.MINOR;
        this.containment = containment == null ? "" : containment.trim();
        this.severity = severity != null ? severity : 1;
        this.likelihood = likelihood != null ? likelihood : 1;
        this.detectability = detectability != null ? detectability : 1;
        this.status = status != null ? status : DeviationStatus.OPEN;
        this.rootCause = rootCause == null ? "" : rootCause.trim();
        this.capaCode = capaCode == null ? "" : capaCode.trim();
        this.reportedBy = reportedBy == null ? "" : reportedBy.trim();
        this.reportedAt = reportedAt;
        this.owner = owner == null ? "" : owner.trim();
        this.dueAt = dueAt;
    }
}
