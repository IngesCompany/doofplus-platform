package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Capa action of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class CapaAction extends AbstractDomainAggregateRoot<CapaAction> {

    /**
     * The identifier; null until the capa action is saved.
     */
    private Long id;

    /**
     * The capa code.
     */
    private String capaCode;

    /**
     * The code.
     */
    private String code;

    /**
     * The type.
     */
    private CapaType type;

    /**
     * The title.
     */
    private String title;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The due at.
     */
    private LocalDate dueAt;

    /**
     * The status.
     */
    private ActionStatus status;

    /**
     * The evidence.
     */
    private String evidence;

    /**
     * The evidence attached.
     */
    private Boolean evidenceAttached;

    private CapaAction() {
    }

    /**
     * Creates the capa action from its command.
     *
     * @param command the data of the capa action
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public CapaAction(CreateCapaActionCommand command) {
        apply(command.capaCode(), command.code(), command.type(), command.title(), command.owner(), command.dueAt(), command.status(), command.evidence(), command.evidenceAttached());
    }

    /**
     * Rebuilds a saved capa action without validating it again.
     *
     * @return the capa action
     */
    public static CapaAction reconstitute(Long id, String capaCode, String code, CapaType type, String title, String owner, LocalDate dueAt, ActionStatus status, String evidence, Boolean evidenceAttached) {
        var capaAction = new CapaAction();
        capaAction.id = id;
        capaAction.capaCode = capaCode;
        capaAction.code = code;
        capaAction.type = type;
        capaAction.title = title;
        capaAction.owner = owner;
        capaAction.dueAt = dueAt;
        capaAction.status = status;
        capaAction.evidence = evidence;
        capaAction.evidenceAttached = evidenceAttached;
        return capaAction;
    }

    /**
     * Replaces the data of the capa action with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateCapaActionCommand command) {
        apply(command.capaCode(), command.code(), command.type(), command.title(), command.owner(), command.dueAt(), command.status(), command.evidence(), command.evidenceAttached());
    }

    private void apply(String capaCode, String code, CapaType type, String title, String owner, LocalDate dueAt, ActionStatus status, String evidence, Boolean evidenceAttached) {
        if (capaCode == null || capaCode.isBlank()) throw new IllegalArgumentException("quality.capaAction.error.capaCode.notBlank");
        this.capaCode = capaCode == null ? "" : capaCode.trim();
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.capaAction.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        this.type = type != null ? type : (this.code.toUpperCase().startsWith("PA-") ? CapaType.PREVENTIVE : CapaType.CORRECTIVE);
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.capaAction.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.owner = owner == null ? "" : owner.trim();
        this.dueAt = dueAt;
        this.status = status != null ? status : ActionStatus.OPEN;
        this.evidence = evidence == null ? "" : evidence.trim();
        this.evidenceAttached = evidenceAttached != null ? evidenceAttached : false;
    }
}
