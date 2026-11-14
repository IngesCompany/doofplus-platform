package com.ingescompany.doofplus.platform.organizations.domain.model.queries;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

/**
 * Query to get the organization registered with a RUC.
 */
public record GetOrganizationByRucQuery(Ruc ruc) {
}
