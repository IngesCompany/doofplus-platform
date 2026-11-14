package com.ingescompany.doofplus.platform.iam.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignInCommand;
import com.ingescompany.doofplus.platform.iam.interfaces.rest.resources.SignInResource;

/**
 * Converts the sign-in request into its command.
 */
public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password(), resource.twoFactorCode());
    }
}
