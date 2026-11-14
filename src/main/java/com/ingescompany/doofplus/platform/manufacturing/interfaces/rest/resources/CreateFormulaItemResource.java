package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource of a line of a master formula.
 */
public record CreateFormulaItemResource(
        String kind,
        String name,
        String role,
        String target,
        String tolerance,
        String control
) {
}
