package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Administrative activity response resource.
 */
public record AdministrativeActivityResource(Long id, Long organizationId, String title, String actor,
                                             LocalDateTime occurredAt) {
}
