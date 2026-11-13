package com.ingescompany.doofplus.platform.organizations.application.internal.commandservices;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.ProfileCommandService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.UpdateProfilePreferencesCommand;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ProfileRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;
    private final OrganizationRepository organizationRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository,
                                     OrganizationRepository organizationRepository) {
        this.profileRepository = profileRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Result<Profile, ApplicationError> handle(CreateProfileCommand command) {
        if (organizationRepository.findById(command.organizationId()).isEmpty()) {
            return Result.failure(ApplicationError.notFound("Organization", command.organizationId().toString()));
        }
        if (profileRepository.findByUserId(command.userId()).isPresent()) {
            return Result.failure(ApplicationError.conflict("Profile",
                    "organizations.error.profile.userId.duplicate"));
        }
        try {
            return Result.success(profileRepository.save(new Profile(command)));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-profile", e.getMessage()));
        }
    }

    @Override
    public Result<Profile, ApplicationError> handle(UpdateProfilePreferencesCommand command) {
        var profileOptional = profileRepository.findById(command.profileId());
        if (profileOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Profile", command.profileId().toString()));
        }
        var profile = profileOptional.get();
        try {
            profile.updatePreferences(command.preferences());
            if (command.language() != null && !command.language().isBlank()) {
                profile.changeLanguage(command.language());
            }
            return Result.success(profileRepository.save(profile));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-profile-preferences", e.getMessage()));
        }
    }
}
