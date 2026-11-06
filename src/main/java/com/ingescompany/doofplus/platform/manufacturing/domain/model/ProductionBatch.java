package com.ingescompany.doofplus.platform.manufacturing.domain.model;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.BatchEvent;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.MaterialConsumption;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.ProcessParameter;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchStatus;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Production Batch aggregate root
 *
 * <p>
 *     Represents a production batch in the manufacturing domain.
 *     This class serves as the aggregate root for the production batch entity,
 *     encapsulating its state and behavior.
 *     It extends AbstractAggregateRoot to leverage Spring Data's aggregate root
 *     capabilities, allowing for domain events and other aggregate-related
 *     functionalities.
 * </p>
 */
@Getter
public class ProductionBatch extends AbstractAggregateRoot<ProductionBatch> {
    /**
     * Unique identifier for the production batch.
     */
    private Long id;

    /**
     * Batch number associated with the production batch.
     */
    private BatchNumber batchNumber;

    /**
     * Identifier of the production order associated with this batch.
     */
    private Long productionOrderId;

    /**
     * Status of the production batch.
     */
    private BatchStatus status;

    /**
     * Planned quantity for the production batch.
     */
    private Quantity plannedQuantity;

    /**
     * Quantity of the production batch that has been manufactured.
     */
    private Quantity manufacturedQuantity;

    /**
     * Timestamp indicating when the production batch was started.
     */
    private LocalDateTime startedAt;

    /**
     * Timestamp indicating when the production batch was finished.
     */
    private LocalDateTime finishedAt;

    /**
     * List of material consumptions for the production batch.
     */
    private final List<MaterialConsumption> consumptions = new ArrayList<>();

    /**
     * List of process parameters for the production batch.
     */
    private final List<ProcessParameter> parameters = new ArrayList<>();

    /**
     * List of incidents for the production batch.
     */
    private final List<Incident> incidents = new ArrayList<>();

    /**
     * List of events for the production batch.
     */
    private final List<BatchEvent> events = new ArrayList<>();

    /**
     * Default constructor for reconstitution.
     */
    public ProductionBatch(CreateBatchCommand command) {
        this.batchNumber = Objects.requireNonNull(command.batchNumber(), "batchNumber must not be null");
        this.productionOrderId = Objects.requireNonNull(command.productionOrderId(), "productionOrderId must not be null");
        this.plannedQuantity = Objects.requireNonNull(command.quantity(), "quantity must not be null");
        this.status = BatchStatus.PLANNED;
        this.recordEvent("BATCH_CREATED", "Production batch created in PLANNED status", null);
    }

    public ProductionBatch() {

    }

    /**
     * Private constructor for reconstitution.
     */
    public static ProductionBatch reconstitute(
            Long id,
            BatchNumber batchNumber,
            Long productionOrderId,
            BatchStatus status,
            Quantity plannedQuantity,
            Quantity manufacturedQuantity,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            List<MaterialConsumption> consumptions,
            List<ProcessParameter> parameters,
            List<Incident> incidents,
            List<BatchEvent> events
    ) {
        ProductionBatch batch = new ProductionBatch();
        batch.id = id;
        batch.batchNumber = batchNumber;
        batch.productionOrderId = productionOrderId;
        batch.status = status;
        batch.plannedQuantity = plannedQuantity;
        batch.manufacturedQuantity = manufacturedQuantity;
        batch.startedAt = startedAt;
        batch.finishedAt = finishedAt;
        if (consumptions != null) batch.consumptions.addAll(consumptions);
        if (parameters != null) batch.parameters.addAll(parameters);
        if (incidents != null) batch.incidents.addAll(incidents);
        if (events != null) batch.events.addAll(events);
        return batch;
    }

    /**
     * Starts the production batch, changing its status to IN_PROGRESS and recording the start time.
     *
     * @throws IllegalStateException if the batch is not in PLANNED status.
     */
    public void start() {
        if (this.status != BatchStatus.PLANNED) {
            throw new IllegalStateException("manufacturing.error.batch.cannotStart");
        }
        this.status = BatchStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
        this.recordEvent("BATCH_STARTED", "Batch manufacturing execution started", null);
    }

    /**
     * Closes the production batch, changing its status to CLOSED and recording the finish time.
     *
     * @param command the command containing the manufactured quantity.
     * @throws IllegalStateException if the batch is already closed.
     */
    public void close(CloseBatchCommand command) {
        if (this.status == BatchStatus.CLOSED) {
            throw new IllegalStateException("manufacturing.error.batch.alreadyClosed");
        }
        this.manufacturedQuantity = Objects.requireNonNull(command.manufacturedQuantity(), "manufacturedQuantity must not be null");
        this.status = BatchStatus.CLOSED;
        this.finishedAt = LocalDateTime.now();
        this.recordEvent("BATCH_CLOSED", "Batch closed with manufactured quantity: " + this.manufacturedQuantity.value(), null);
    }

    /**
     * Registers an incident for the production batch, adding it to the list of incidents.
     * If the incident severity is CRITICAL, the batch status is changed to ON_HOLD.
     *
     * @param command the command containing the incident details.
     * @return the registered incident.
     */
    public Incident registerIncident(RegisterIncidentCommand command) {
        Incident incident = new Incident(command.description(), command.severity());
        this.incidents.add(incident);
        if (command.severity() == IncidentSeverity.CRITICAL) {
            this.status = BatchStatus.ON_HOLD;
        }
        this.recordEvent("INCIDENT_REGISTERED", "Incident registered: " + command.description(), null);
        return incident;
    }

    /**
     * Consumes materials for the production batch, adding a MaterialConsumption record.
     *
     * @param rawMaterialLotId the identifier of the raw material lot.
     * @param quantity the quantity of materials consumed.
     */
    public void consumeMaterial(Long rawMaterialLotId, Quantity quantity) {
        this.consumptions.add(new MaterialConsumption(rawMaterialLotId, quantity));
        this.recordEvent("MATERIAL_CONSUMED", "Raw material lot " + rawMaterialLotId + " consumed", null);
    }

    /**
     * Records a process parameter for the production batch, adding a ProcessParameter record.
     *
     * @param name the name of the process parameter.
     * @param value the value of the process parameter.
     * @param unit the unit of the process parameter.
     */
    public void recordParameter(String name, BigDecimal value, String unit) {
        this.parameters.add(new ProcessParameter(name, value, unit));
    }

    /**
     * Records a batch event, adding it to the list of events.
     *
     * @param type the type of the batch event.
     * @param description a description of the batch event.
     * @param performedBy the identifier of the user or system that performed the action.
     */
    private void recordEvent(String type, String description, Long performedBy) {
        this.events.add(new BatchEvent(type, description, performedBy));
    }

    /**
     * Returns an unmodifiable view of the list of material consumptions for the production batch.
     *
     * @return an unmodifiable list of MaterialConsumption.
     */
    public List<MaterialConsumption> getConsumptions() {
        return Collections.unmodifiableList(this.consumptions);
    }

    /**
     * Returns an unmodifiable view of the list of process parameters for the production batch.
     *
     * @return an unmodifiable list of ProcessParameter.
     */
    public List<ProcessParameter> getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    /**
     * Returns an unmodifiable view of the list of incidents for the production batch.
     *
     * @return an unmodifiable list of Incident.
     */
    public List<Incident> getIncidents() {
        return Collections.unmodifiableList(this.incidents);
    }

    /**
     * Returns an unmodifiable view of the list of batch events for the production batch.
     *
     * @return an unmodifiable list of BatchEvent.
     */
    public List<BatchEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }
}
