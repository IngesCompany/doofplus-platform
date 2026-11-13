package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Facility response resource. The type and the status go in kebab case.
 */
public record FacilityResource(Long id, Long organizationId, String name, String type, String timezone,
                               String ownerName, String status) {
}
