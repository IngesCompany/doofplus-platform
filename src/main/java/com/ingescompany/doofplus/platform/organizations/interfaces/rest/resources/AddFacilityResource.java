package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Add facility request resource. The type goes in kebab case (manufacturing or qc-laboratory).
 */
public record AddFacilityResource(Long organizationId, String name, String type, String timezone) {
}
