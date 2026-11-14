package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Organization response resource. The status goes in kebab case (for example, pending-verification).
 */
public record OrganizationResource(Long id, String legalName, String ruc, String region, String ownerName,
                                   String status) {
}
