package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.AllocateRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.InspectRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ReceiveRawMaterialLotCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.MaterialCategory;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.RawMaterialLotStatus;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.SamplingStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Raw material lot received by the laboratory. As in the Web App, the receipt sends it to sampling,
 * QC inspects it and then it is approved or rejected; an approved lot can be allocated to a batch.
 */
@Getter
public class RawMaterialLot extends AbstractDomainAggregateRoot<RawMaterialLot> {
    private Long id;
    private String code;
    private String receiptCode;
    private String material;
    private MaterialCategory category;
    private String supplier;
    private String supplierLot;
    private String quantity;
    private LocalDate expiryDate;
    private String storageLocation;
    private String certificate;
    private RawMaterialLotStatus status;
    private boolean packagingVerified;
    private boolean identityVerified;
    private SamplingStatus samplingStatus;
    private String allocatedBatch;

    private RawMaterialLot() {
    }

    /**
     * Receives a lot. It goes to sampling with the sample pending, as in the Web App.
     *
     * @param command data of the receipt
     */
    public RawMaterialLot(ReceiveRawMaterialLotCommand command) {
        this.code = command.code();
        this.receiptCode = trimToEmpty(command.receiptCode());
        this.material = command.material().trim();
        this.category = command.category();
        this.supplier = trimToEmpty(command.supplier());
        this.supplierLot = trimToEmpty(command.supplierLot());
        this.quantity = trimToEmpty(command.quantity());
        this.expiryDate = command.expiryDate();
        this.storageLocation = trimToEmpty(command.storageLocation());
        this.certificate = trimToEmpty(command.certificate());
        this.status = RawMaterialLotStatus.SAMPLING;
        this.samplingStatus = SamplingStatus.PENDING;
        this.allocatedBatch = "";
    }

    /**
     * Rebuilds a stored lot.
     *
     * @return the lot with its persistent identifier
     */
    public static RawMaterialLot reconstitute(Long id, String code, String receiptCode, String material,
                                              MaterialCategory category, String supplier, String supplierLot,
                                              String quantity, LocalDate expiryDate, String storageLocation,
                                              String certificate, RawMaterialLotStatus status,
                                              boolean packagingVerified, boolean identityVerified,
                                              SamplingStatus samplingStatus, String allocatedBatch) {
        var lot = new RawMaterialLot();
        lot.id = id;
        lot.code = code;
        lot.receiptCode = receiptCode;
        lot.material = material;
        lot.category = category;
        lot.supplier = supplier;
        lot.supplierLot = supplierLot;
        lot.quantity = quantity;
        lot.expiryDate = expiryDate;
        lot.storageLocation = storageLocation;
        lot.certificate = certificate;
        lot.status = status;
        lot.packagingVerified = packagingVerified;
        lot.identityVerified = identityVerified;
        lot.samplingStatus = samplingStatus;
        lot.allocatedBatch = allocatedBatch;
        return lot;
    }

    /**
     * Records the QC inspection of a lot in quarantine, sampling or inspection.
     *
     * @param command checks, sampling status and storage location
     * @throws IllegalStateException when the lot was already approved or rejected
     */
    public void inspect(InspectRawMaterialLotCommand command) {
        if (status == RawMaterialLotStatus.APPROVED || status == RawMaterialLotStatus.REJECTED) {
            throw new IllegalStateException("manufacturing.error.lot.closed");
        }
        this.status = RawMaterialLotStatus.INSPECTION;
        this.packagingVerified = command.packagingVerified();
        this.identityVerified = command.identityVerified();
        this.samplingStatus = command.samplingStatus();
        if (command.storageLocation() != null && !command.storageLocation().isBlank()) {
            this.storageLocation = command.storageLocation().trim();
        }
    }

    /**
     * Approves a lot in inspection with both checks and the sample done.
     *
     * @throws IllegalStateException when it is not in inspection or the inspection is incomplete
     */
    public void approve() {
        if (status != RawMaterialLotStatus.INSPECTION) throw new IllegalStateException("manufacturing.error.lot.notInInspection");
        if (!packagingVerified || !identityVerified || samplingStatus != SamplingStatus.DONE) {
            throw new IllegalStateException("manufacturing.error.lot.inspectionIncomplete");
        }
        this.status = RawMaterialLotStatus.APPROVED;
    }

    /**
     * Rejects a lot in inspection.
     *
     * @throws IllegalStateException when it is not in inspection
     */
    public void reject() {
        if (status != RawMaterialLotStatus.INSPECTION) throw new IllegalStateException("manufacturing.error.lot.notInInspection");
        this.status = RawMaterialLotStatus.REJECTED;
    }

    /**
     * Allocates an approved lot to a production batch.
     *
     * @param command code of the batch
     * @throws IllegalStateException when the lot is not approved
     */
    public void allocate(AllocateRawMaterialLotCommand command) {
        if (status != RawMaterialLotStatus.APPROVED) throw new IllegalStateException("manufacturing.error.lot.notApproved");
        this.allocatedBatch = command.batchCode();
    }

    private static String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
