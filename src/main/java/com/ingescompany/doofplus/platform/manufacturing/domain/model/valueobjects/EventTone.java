package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Tone of an event in the timeline of a production batch, as the Web App shows it.
 */
public enum EventTone {
    /**
     * Regular step of the batch.
     */
    NORMAL,
    /**
     * Something that needs attention, for example a major incident.
     */
    WARNING,
    /**
     * Something that stops the batch, for example a critical incident.
     */
    DANGER
}
