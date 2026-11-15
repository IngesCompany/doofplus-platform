package com.ingescompany.doofplus.platform.quality.domain.model.valueobjects;

/**
 * Number of deviations of a root cause in a month; a read model built from the deviations.
 *
 * @param month     the month, in yyyy-MM
 * @param rootCause the root cause
 * @param count     the number of deviations
 */
public record DeviationTrend(String month, String rootCause, int count) {
}
