package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateBatchProgressCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.BatchEvent;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.MaterialConsumption;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.ProcessParameter;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchStatus;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.EventTone;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import lombok.Getter;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
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
public class ProductionBatch extends AbstractDomainAggregateRoot<ProductionBatch> {
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
     * Production line where the batch runs, for example {@code Line 02}.
     */
    private String line;

    /**
     * Name of the person in charge of the batch, as the Web App shows it.
     */
    private String owner;

    /**
     * Progress of the batch, from 0 to 100.
     */
    private int progress;

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
        this.line = blankToNull(command.line());
        this.owner = blankToNull(command.owner());
        this.progress = 0;
        this.status = BatchStatus.PLANNED;
        this.recordEvent("BATCH_CREATED", "Production batch created in PLANNED status", EventTone.NORMAL, null);
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
            String line,
            String owner,
            int progress,
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
        batch.line = line;
        batch.owner = owner;
        batch.progress = progress;
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
        this.recordEvent("BATCH_STARTED", "Batch manufacturing execution started", EventTone.NORMAL, null);
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
        this.recordEvent("BATCH_CLOSED", "Batch closed with manufactured quantity: " + this.manufacturedQuantity.value(), EventTone.NORMAL, null);
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
        EventTone tone = switch (command.severity()) {
            case CRITICAL -> EventTone.DANGER;
            case MAJOR -> EventTone.WARNING;
            default -> EventTone.NORMAL;
        };
        this.recordEvent("INCIDENT_REGISTERED", "Incident registered (" + command.severity() + "): " + command.description(), tone, null);
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
        this.recordEvent("MATERIAL_CONSUMED", "Raw material lot " + rawMaterialLotId + " consumed", EventTone.NORMAL, null);
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
    private void recordEvent(String type, String description, EventTone tone, Long performedBy) {
        this.events.add(new BatchEvent(type, description, tone, performedBy));
    }

    /**
     * Returns an unmodifiable view of the list of material consumptions for the production batch.
     *
     * @return an unmodifiable list of MaterialConsumption.
     */
    public void updateProgress(UpdateBatchProgressCommand command) {
        this.progress = command.progress();
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

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
