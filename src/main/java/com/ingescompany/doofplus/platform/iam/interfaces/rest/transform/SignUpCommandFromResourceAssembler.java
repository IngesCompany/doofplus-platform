package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.List;

/**
 * Converts the sign-up request into its command. The role key (qa-specialist) becomes ROLE_QA_SPECIALIST.
 */
public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.role() == null || resource.role().isBlank()
                ? List.<Role>of()
                : List.of(Role.toRoleFromName(resource.role()));
        return new SignUpCommand(resource.email(), resource.password(), resource.fullName(), roles,
                resource.environment(), resource.facility(), resource.status(), resource.privilege(),
                resource.organizationId(), resource.organizationName(), resource.plant(), resource.twoFactorCode());
    }
}
