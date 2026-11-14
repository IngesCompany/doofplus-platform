package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ApproveMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.FormulaItem;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.FormulaStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Master formula of a product: its components and process parameters for one batch size.
 * It is linked to the product by its code, as the Web App does.
 */
@Getter
public class MasterFormula extends AbstractDomainAggregateRoot<MasterFormula> {
    private Long id;
    private String productCode;
    private String version;
    private FormulaStatus status;
    private String approvedBy;
    private LocalDate approvedAt;
    private BigDecimal batchSize;
    private final List<FormulaItem> items = new ArrayList<>();

    private MasterFormula() {
    }

    /**
     * Writes a master formula. It starts as a draft.
     *
     * @param command product code, version, batch size and items
     */
    public MasterFormula(CreateMasterFormulaCommand command) {
        this.productCode = command.productCode();
        this.version = command.version();
        this.batchSize = command.batchSize();
        this.status = FormulaStatus.DRAFT;
        this.approvedBy = "";
        command.items().forEach(item -> this.items.add(new FormulaItem(item)));
    }

    /**
     * Rebuilds a stored master formula.
     *
     * @return the master formula with its persistent identifier
     */
    public static MasterFormula reconstitute(Long id, String productCode, String version, FormulaStatus status,
                                             String approvedBy, LocalDate approvedAt, BigDecimal batchSize,
                                             List<FormulaItem> items) {
        var formula = new MasterFormula();
        formula.id = id;
        formula.productCode = productCode;
        formula.version = version;
        formula.status = status;
        formula.approvedBy = approvedBy;
        formula.approvedAt = approvedAt;
        formula.batchSize = batchSize;
        if (items != null) formula.items.addAll(items);
        return formula;
    }

    /**
     * Approves a draft or pending formula.
     *
     * @param command name of the person who approves it
     * @throws IllegalStateException when the formula is already approved or locked
     */
    public void approve(ApproveMasterFormulaCommand command) {
        if (status != FormulaStatus.DRAFT && status != FormulaStatus.PENDING) {
            throw new IllegalStateException("manufacturing.error.formula.notDraft");
        }
        this.status = FormulaStatus.APPROVED;
        this.approvedBy = command.approvedBy();
        this.approvedAt = LocalDate.now();
    }

    /**
     * Locks an approved formula when a newer version of the product is approved.
     */
    public void lock() {
        if (status == FormulaStatus.APPROVED) this.status = FormulaStatus.LOCKED;
    }

    /**
     * @return {@code true} when it is the effective version of the product
     */
    public boolean isApproved() {
        return status == FormulaStatus.APPROVED;
    }

    public List<FormulaItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
