package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing a line of a master formula.
 */
public record FormulaItemResource(
        Long id,
        String kind,
        String name,
        String role,
        String target,
        String tolerance,
        String control
) {
}
