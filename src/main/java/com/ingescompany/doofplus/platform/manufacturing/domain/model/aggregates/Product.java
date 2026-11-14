package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateProductCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.DosageForm;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Product of the catalog of the laboratory. The Web App links its formulas, orders and batches by its code.
 */
@Getter
public class Product extends AbstractDomainAggregateRoot<Product> {
    private Long id;
    private String code;
    private String name;
    private DosageForm dosageForm;

    private Product() {
    }

    /**
     * Registers a product.
     *
     * @param command code, name and dosage form
     */
    public Product(CreateProductCommand command) {
        this.code = command.code();
        this.name = command.name();
        this.dosageForm = command.dosageForm();
    }

    /**
     * Rebuilds a stored product.
     *
     * @return the product with its persistent identifier
     */
    public static Product reconstitute(Long id, String code, String name, DosageForm dosageForm) {
        var product = new Product();
        product.id = id;
        product.code = code;
        product.name = name;
        product.dosageForm = dosageForm;
        return product;
    }

    /**
     * Updates the name and the dosage form. The code does not change.
     *
     * @param command new name and dosage form
     */
    public void update(UpdateProductCommand command) {
        this.name = command.name();
        this.dosageForm = command.dosageForm();
    }
}
