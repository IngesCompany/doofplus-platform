package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Capa plan of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class CapaPlan extends AbstractDomainAggregateRoot<CapaPlan> {

    /**
     * The identifier; null until the capa plan is saved.
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
     * The source deviation.
     */
    private String sourceDeviation;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The reviewer.
     */
    private String reviewer;

    /**
     * The root cause.
     */
    private String rootCause;

    /**
     * The acceptance criterion.
     */
    private String acceptanceCriterion;

    /**
     * The effectiveness check at.
     */
    private LocalDate effectivenessCheckAt;

    /**
     * The status.
     */
    private CapaStatus status;

    private CapaPlan() {
    }

    /**
     * Creates the capa plan from its command.
     *
     * @param command the data of the capa plan
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public CapaPlan(CreateCapaPlanCommand command) {
        apply(command.code(), command.title(), command.sourceDeviation(), command.owner(), command.reviewer(), command.rootCause(), command.acceptanceCriterion(), command.effectivenessCheckAt(), command.status());
    }

    /**
     * Rebuilds a saved capa plan without validating it again.
     *
     * @return the capa plan
     */
    public static CapaPlan reconstitute(Long id, String code, String title, String sourceDeviation, String owner, String reviewer, String rootCause, String acceptanceCriterion, LocalDate effectivenessCheckAt, CapaStatus status) {
        var capaPlan = new CapaPlan();
        capaPlan.id = id;
        capaPlan.code = code;
        capaPlan.title = title;
        capaPlan.sourceDeviation = sourceDeviation;
        capaPlan.owner = owner;
        capaPlan.reviewer = reviewer;
        capaPlan.rootCause = rootCause;
        capaPlan.acceptanceCriterion = acceptanceCriterion;
        capaPlan.effectivenessCheckAt = effectivenessCheckAt;
        capaPlan.status = status;
        return capaPlan;
    }

    /**
     * Replaces the data of the capa plan with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateCapaPlanCommand command) {
        apply(command.code(), command.title(), command.sourceDeviation(), command.owner(), command.reviewer(), command.rootCause(), command.acceptanceCriterion(), command.effectivenessCheckAt(), command.status());
    }

    private void apply(String code, String title, String sourceDeviation, String owner, String reviewer, String rootCause, String acceptanceCriterion, LocalDate effectivenessCheckAt, CapaStatus status) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.capaPlan.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.capaPlan.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.sourceDeviation = sourceDeviation == null ? "" : sourceDeviation.trim();
        this.owner = owner == null ? "" : owner.trim();
        this.reviewer = reviewer == null ? "" : reviewer.trim();
        this.rootCause = rootCause == null ? "" : rootCause.trim();
        this.acceptanceCriterion = acceptanceCriterion == null ? "" : acceptanceCriterion.trim();
        this.effectivenessCheckAt = effectivenessCheckAt;
        this.status = status != null ? status : CapaStatus.DRAFT;
    }
}
