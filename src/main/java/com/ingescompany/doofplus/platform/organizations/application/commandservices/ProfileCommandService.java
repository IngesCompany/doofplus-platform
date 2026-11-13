package com.ingescompany.doofplus.platform.organizations.application.commandservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.UpdateProfilePreferencesCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface ProfileCommandService {
    Result<Profile, ApplicationError> handle(CreateProfileCommand command);

    Result<Profile, ApplicationError> handle(UpdateProfilePreferencesCommand command);
}
