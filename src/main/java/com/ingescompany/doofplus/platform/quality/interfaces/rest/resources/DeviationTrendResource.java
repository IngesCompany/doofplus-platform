package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Point of the deviation trend; id is its position in the list.
 *
 * @param id        the position in the list, from 1
 * @param rootCause the root cause
 * @param month     the month, in yyyy-MM
 * @param count     the number of deviations
 */
public record DeviationTrendResource(int id, String rootCause, String month, int count) {
}
