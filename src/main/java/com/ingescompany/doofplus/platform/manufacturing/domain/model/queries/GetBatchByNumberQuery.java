package com.ingescompany.doofplus.platform.manufacturing.domain.model.queries;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;

/**
    * Query for retrieving a batch by its number.
    *
    * @param batchNumber batch number
 **/

public record GetBatchByNumberQuery(BatchNumber batchNumber) {
    public GetBatchByNumberQuery{
        if (batchNumber == null) {
            throw new IllegalArgumentException("batch.error.number.invalid");
        }
    }
}
