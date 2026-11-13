package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import lombok.Getter;

import java.util.Objects;

/**
 * MaterialConsumption domain entity
 *
 * <p>
 *     Represents the consumption of materials in the manufacturing process.
 *     This entity is responsible for tracking the amount of materials consumed
 *     during production, ensuring accurate inventory management and cost calculation.
 * </p>
 */

@Getter
public class MaterialConsumption {
    /**
     * Unique identifier for the material consumption record.
     */
    private Long id;

    /**
     * Identifier of the production batch associated with this material consumption.
     */
    private Long rawMaterialLotId;

    /**
     * Quantity of materials consumed in the manufacturing process.
     */
    private Quantity quantityUsed;

    public MaterialConsumption(){

    }

    /**
     * Constructor for MaterialConsumption.
     * @param rawMaterialLotId The identifier of the raw material lot.
     * @param quantityUsed The quantity of materials consumed.
     */
    public MaterialConsumption(Long rawMaterialLotId, Quantity quantityUsed) {
        this.rawMaterialLotId = Objects.requireNonNull(rawMaterialLotId, "rawMaterialLotId must not be null");
        this.quantityUsed = Objects.requireNonNull(quantityUsed, "quantityUsed must not be null");
    }

    /**
     * Private constructor for reconstitution.
     */
    public static MaterialConsumption reconstitute(Long id, Long rawMaterialLotId, Quantity quantityUsed) {
        MaterialConsumption consumption = new MaterialConsumption();
        consumption.id = id;
        consumption.rawMaterialLotId = rawMaterialLotId;
        consumption.quantityUsed = quantityUsed;
        return consumption;
    }
}
