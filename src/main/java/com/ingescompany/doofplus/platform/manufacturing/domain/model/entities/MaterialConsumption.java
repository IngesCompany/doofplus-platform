package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MaterialConsumption domain entity
 *
 * <p>
 *     Represents the consumption of a raw material lot in a production batch.
 *     It keeps the consumed quantity and when it was consumed, for the traceability of the batch.
 * </p>
 */
@Getter
public class MaterialConsumption {

    /**
     * Unique identifier for the material consumption record.
     */
    private Long id;

    /**
     * Identifier of the raw material lot that was consumed.
     */
    private Long rawMaterialLotId;

    /**
     * Quantity of the raw material lot consumed by the batch.
     */
    private Quantity consumedQuantity;

    /**
     * Date and time when the material was consumed.
     */
    private LocalDateTime consumedAt;

    public MaterialConsumption() {
    }

    /**
     * Creates a material consumption recorded now.
     *
     * @param rawMaterialLotId identifier of the raw material lot
     * @param consumedQuantity quantity consumed
     */
    public MaterialConsumption(Long rawMaterialLotId, Quantity consumedQuantity) {
        this.rawMaterialLotId = Objects.requireNonNull(rawMaterialLotId, "rawMaterialLotId must not be null");
        this.consumedQuantity = Objects.requireNonNull(consumedQuantity, "consumedQuantity must not be null");
        this.consumedAt = LocalDateTime.now();
    }

    /**
     * Rebuilds a stored material consumption.
     *
     * @return the material consumption with its persistent identifier
     */
    public static MaterialConsumption reconstitute(Long id, Long rawMaterialLotId, Quantity consumedQuantity,
                                                   LocalDateTime consumedAt) {
        MaterialConsumption consumption = new MaterialConsumption();
        consumption.id = id;
        consumption.rawMaterialLotId = rawMaterialLotId;
        consumption.consumedQuantity = consumedQuantity;
        consumption.consumedAt = consumedAt;
        return consumption;
    }
}
