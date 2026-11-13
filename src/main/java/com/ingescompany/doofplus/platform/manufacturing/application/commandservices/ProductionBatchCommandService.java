package com.ingescompany.doofplus.platform.manufacturing.application.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for commands over the {@link ProductionBatch} aggregate
 */
public interface ProductionBatchCommandService {
    /**
     * Handles the creation of a new production batch.
     *
     * @param command The command containing the details for creating the batch.
     * @return A result containing the created {@link ProductionBatch} or an {@link ApplicationError}.
     */
    Result<ProductionBatch, ApplicationError> handle(CreateBatchCommand command);

    /**
     * Handles the closing of a production batch.
     *
     * @param command The command containing the details for closing the batch.
     * @return A result containing the closed {@link ProductionBatch} or an {@link ApplicationError}.
     */
    Result<ProductionBatch, ApplicationError> handle(CloseBatchCommand command);

    /**
     * Handles the registration of a new incident.
     *
     * @param command The command containing the details for registering the incident.
     * @return A result containing the registered {@link Incident} or an {@link ApplicationError}.
     */
    Result<Incident, ApplicationError> handle(RegisterIncidentCommand command);
}
