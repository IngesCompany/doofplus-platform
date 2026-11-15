package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.UserResource;

/**
 * Converts a user into its resource. The password and the two-factor code never leave the API.
 */
public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).sorted().toList();
        return new UserResource(user.getId(), user.getFullName(), user.getInitials(), user.getEmail(),
                user.getRoleKey(), user.getEnvironment(), user.getFacility(), user.getStatus(), user.getPrivilege(),
                user.getOrganizationId(), user.getOrganizationName(), user.getPlant(), roles);
    }
}
