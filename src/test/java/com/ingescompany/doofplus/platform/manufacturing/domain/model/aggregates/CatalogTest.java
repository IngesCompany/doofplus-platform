package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.*;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CatalogTest {

    private static MasterFormula formula() {
        return new MasterFormula(new CreateMasterFormulaCommand("AC500", "v3.2", new BigDecimal("120000"),
                List.of(new FormulaItemData(FormulaItemKind.COMPONENT, "Paracetamol (API)", "Active ingredient",
                        "60.0 kg", "± 2.0 %", "Meridian Ingredients"))));
    }

    private static RawMaterialLot lot() {
        return new RawMaterialLot(new ReceiveRawMaterialLotCommand("RM-26098", "RCV-26098", "Paracetamol (API)",
                MaterialCategory.API, "Meridian Ingredients", "MI-AC-0926", "500 kg · 20 sealed drums",
                LocalDate.of(2028, 9, 30), "Q-A12 · Quarantine zone", "CoA_MI-AC-0926.pdf"));
    }

    @Test
    void aProductNeedsCodeNameAndDosageForm() {
        assertThatThrownBy(() -> new CreateProductCommand(" ", "Paracetamol", DosageForm.TABLET))
                .hasMessage("manufacturing.error.product.code.notBlank");
        assertThatThrownBy(() -> new CreateProductCommand("AC500", "Paracetamol", null))
                .hasMessage("manufacturing.error.product.dosageForm.invalid");
        assertThat(new Product(new CreateProductCommand(" AC500 ", "Paracetamol 500 mg", DosageForm.TABLET)).getCode())
                .isEqualTo("AC500");
    }

    @Test
    void aFormulaStartsAsDraftAndCanBeApprovedOnce() {
        var formula = formula();
        assertThat(formula.getStatus()).isEqualTo(FormulaStatus.DRAFT);
        assertThat(formula.getApprovedBy()).isEmpty();

        formula.approve(new ApproveMasterFormulaCommand(1L, "Lucia Paredes"));

        assertThat(formula.isApproved()).isTrue();
        assertThat(formula.getApprovedAt()).isEqualTo(LocalDate.now());
        assertThatThrownBy(() -> formula.approve(new ApproveMasterFormulaCommand(1L, "Lucia Paredes")))
                .hasMessage("manufacturing.error.formula.notDraft");
        formula.lock();
        assertThat(formula.getStatus()).isEqualTo(FormulaStatus.LOCKED);
    }

    @Test
    void anOrderFollowsTheAllowedStatusChanges() {
        var order = new ProductionOrder(new CreateProductionOrderCommand("PO-26041", 1L, "B-26041",
                new BigDecimal("120000"), "Line 02", LocalDateTime.of(2026, 10, 7, 6, 0), null,
                List.of(new OperationData(2, "Granulation", "EQ-GRAN-01"), new OperationData(1, "Dispensing", null))),
                "AC500");

        assertThat(order.getOperations()).extracting("name").containsExactly("Dispensing", "Granulation");
        assertThatThrownBy(() -> order.changeStatus(ProductionOrderStatus.COMPLETED))
                .hasMessage("manufacturing.error.order.transition.invalid");
        order.changeStatus(ProductionOrderStatus.IN_PROGRESS);
        order.changeStatus(ProductionOrderStatus.ON_HOLD);
        order.changeStatus(ProductionOrderStatus.IN_PROGRESS);
        order.changeStatus(ProductionOrderStatus.COMPLETED);
        assertThat(order.getStatus()).isEqualTo(ProductionOrderStatus.COMPLETED);
        assertThat(order.updateOperation(new UpdateOperationCommand(1L, 1, 100, OperationStatus.COMPLETE, null))).isTrue();
        assertThat(order.updateOperation(new UpdateOperationCommand(1L, 9, 100, OperationStatus.COMPLETE, null))).isFalse();
    }

    @Test
    void anOrderRejectsRepeatedSequencesAndWrongDates() {
        assertThatThrownBy(() -> new CreateProductionOrderCommand("PO-1", 1L, null, BigDecimal.TEN, null, null, null,
                List.of(new OperationData(1, "A", null), new OperationData(1, "B", null))))
                .hasMessage("manufacturing.error.order.sequence.duplicate");
        assertThatThrownBy(() -> new CreateProductionOrderCommand("PO-1", 1L, null, BigDecimal.TEN, null,
                LocalDateTime.of(2026, 10, 8, 6, 0), LocalDateTime.of(2026, 10, 7, 6, 0), null))
                .hasMessage("manufacturing.error.order.plannedDates.invalid");
    }

    @Test
    void aLotGoesFromSamplingToInspectionAndThenIsApprovedAndAllocated() {
        var lot = lot();
        assertThat(lot.getStatus()).isEqualTo(RawMaterialLotStatus.SAMPLING);
        assertThat(lot.getSamplingStatus()).isEqualTo(SamplingStatus.PENDING);

        lot.inspect(new InspectRawMaterialLotCommand(1L, true, true, SamplingStatus.REQUESTED, null));
        assertThatThrownBy(lot::approve).hasMessage("manufacturing.error.lot.inspectionIncomplete");

        lot.inspect(new InspectRawMaterialLotCommand(1L, true, true, SamplingStatus.DONE, "W-B07 · Released stock"));
        lot.approve();
        lot.allocate(new AllocateRawMaterialLotCommand(1L, "B-26041"));

        assertThat(lot.getStatus()).isEqualTo(RawMaterialLotStatus.APPROVED);
        assertThat(lot.getStorageLocation()).isEqualTo("W-B07 · Released stock");
        assertThat(lot.getAllocatedBatch()).isEqualTo("B-26041");
        assertThatThrownBy(() -> lot.inspect(new InspectRawMaterialLotCommand(1L, true, true, SamplingStatus.DONE, null)))
                .hasMessage("manufacturing.error.lot.closed");
    }

    @Test
    void onlyAnApprovedLotCanBeAllocatedAndOnlyALotInInspectionRejected() {
        var lot = lot();

        assertThatThrownBy(() -> lot.allocate(new AllocateRawMaterialLotCommand(1L, "B-26041")))
                .hasMessage("manufacturing.error.lot.notApproved");
        assertThatThrownBy(lot::reject).hasMessage("manufacturing.error.lot.notInInspection");
    }
}
