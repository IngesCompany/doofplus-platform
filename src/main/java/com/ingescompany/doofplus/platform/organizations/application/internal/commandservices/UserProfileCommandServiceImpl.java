package com.ingescompany.doofplus.platform.organizations.application.internal.commandservices;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.UserProfileCommandService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateUserProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.UpdateUserProfilePreferencesCommand;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.UserProfileRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class UserProfileCommandServiceImpl implements UserProfileCommandService {
    private final UserProfileRepository userProfileRepository;
    private final OrganizationRepository organizationRepository;

    public UserProfileCommandServiceImpl(UserProfileRepository userProfileRepository,
                                     OrganizationRepository organizationRepository) {
        this.userProfileRepository = userProfileRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Result<UserProfile, ApplicationError> handle(CreateUserProfileCommand command) {
        if (organizationRepository.findById(command.organizationId()).isEmpty()) {
            return Result.failure(ApplicationError.notFound("Organization", command.organizationId().toString()));
        }
        if (userProfileRepository.findByUserId(command.userId()).isPresent()) {
            return Result.failure(ApplicationError.conflict("UserProfile",
                    "organizations.error.profile.userId.duplicate"));
        }
        try {
            return Result.success(userProfileRepository.save(new UserProfile(command)));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-profile", e.getMessage()));
        }
    }

    @Override
    public Result<UserProfile, ApplicationError> handle(UpdateUserProfilePreferencesCommand command) {
        var profileOptional = userProfileRepository.findById(command.profileId());
        if (profileOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("UserProfile", command.profileId().toString()));
        }
        var profile = profileOptional.get();
        try {
            profile.updatePreferences(command.preferences());
            if (command.language() != null && !command.language().isBlank()) {
                profile.changeLanguage(command.language());
            }
            return Result.success(userProfileRepository.save(profile));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-profile-preferences", e.getMessage()));
        }
    }
}
