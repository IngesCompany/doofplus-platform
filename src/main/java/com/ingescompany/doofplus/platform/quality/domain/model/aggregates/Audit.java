package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Audit of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class Audit extends AbstractDomainAggregateRoot<Audit> {

    /**
     * The identifier; null until the audit is saved.
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
     * The period.
     */
    private String period;

    /**
     * The areas reviewed.
     */
    private Integer areasReviewed;

    /**
     * The areas total.
     */
    private Integer areasTotal;

    /**
     * The status.
     */
    private AuditStatus status;

    private Audit() {
    }

    /**
     * Creates the audit from its command.
     *
     * @param command the data of the audit
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public Audit(CreateAuditCommand command) {
        apply(command.code(), command.title(), command.period(), command.areasReviewed(), command.areasTotal(), command.status());
    }

    /**
     * Rebuilds a saved audit without validating it again.
     *
     * @return the audit
     */
    public static Audit reconstitute(Long id, String code, String title, String period, Integer areasReviewed, Integer areasTotal, AuditStatus status) {
        var audit = new Audit();
        audit.id = id;
        audit.code = code;
        audit.title = title;
        audit.period = period;
        audit.areasReviewed = areasReviewed;
        audit.areasTotal = areasTotal;
        audit.status = status;
        return audit;
    }

    private void apply(String code, String title, String period, Integer areasReviewed, Integer areasTotal, AuditStatus status) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.audit.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.audit.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.period = period == null ? "" : period.trim();
        this.areasReviewed = areasReviewed != null ? areasReviewed : 0;
        this.areasTotal = areasTotal != null ? areasTotal : 0;
        this.status = status != null ? status : AuditStatus.SCHEDULED;
    }
}
