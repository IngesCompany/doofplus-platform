package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

import java.util.List;

/**
 * Organization response resource.
 */
public record OrganizationResource(Long id, String legalName, String ruc, String status, List<PlantResource> plants) {
}
