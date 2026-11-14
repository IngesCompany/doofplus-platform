package com.ingescompany.doofplus.platform.organizations.application.commandservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateUserProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.UpdateUserProfilePreferencesCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface UserProfileCommandService {
    Result<UserProfile, ApplicationError> handle(CreateUserProfileCommand command);

    Result<UserProfile, ApplicationError> handle(UpdateUserProfilePreferencesCommand command);
}
