package com.ingescompany.doofplus.platform.manufacturing.domain.model.queries;

/**
 * Query to get the master formulas, optionally of one product.
 */
public record GetAllMasterFormulasQuery(String productCode) {
}
