package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Converts the signed-in user and its token into the resource.
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getFullName(), user.getInitials(), user.getEmail(),
                user.getRoleKey(), user.getEnvironment(), user.getFacility(), user.getStatus(), user.getPrivilege(),
                user.getOrganizationId(), user.getOrganizationName(), user.getPlant(), token);
    }
}
