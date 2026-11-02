package com.ingescompany.doofplus.platform.manufacturing.domain.model.queries;

/**
 * Query for retrieving a batch by its identifier.
 *
 * @param id batch identifier
 */
public record GetBatchByIdQuery(Long id) {
    public GetBatchByIdQuery{
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("batch.error.id.invalid");
        }
    }
}
