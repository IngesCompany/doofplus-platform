package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the collaboration tasks, optionally filtered.
 *
 * @param recordCode the record code to filter by, or null for all
 */
public record GetAllCollaborationTasksQuery(String recordCode) {
}
