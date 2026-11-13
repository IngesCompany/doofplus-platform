package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Register organization request resource.
 */
public record RegisterOrganizationResource(String legalName, String ruc, String region, String ownerName) {
}
