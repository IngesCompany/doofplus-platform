package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.CreateUserProfileCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Area;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.NotificationPreferences;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.PersonName;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.Set;

/**
 * UserProfile aggregate root: the personal data, area, site and preferences of a user of an organization.
 */
@Getter
public class UserProfile extends AbstractDomainAggregateRoot<UserProfile> {
    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "es");
    private static final String DEFAULT_LANGUAGE = "en";

    private Long id;
    private Long userId;
    private Long organizationId;
    private PersonName name;
    private String email;
    private Area area;
    private String site;
    private String language;
    private NotificationPreferences preferences;

    public UserProfile(CreateUserProfileCommand command) {
        this.userId = command.userId();
        this.organizationId = command.organizationId();
        this.name = command.name();
        this.email = command.email();
        this.area = command.area();
        this.site = command.site();
        this.language = DEFAULT_LANGUAGE;
        this.preferences = new NotificationPreferences(true, true);
        if (command.language() != null && !command.language().isBlank()) {
            this.changeLanguage(command.language());
        }
    }

    private UserProfile() {
    }

    public static UserProfile reconstitute(Long id, Long userId, Long organizationId, PersonName name, String email,
                                       Area area, String site, String language,
                                       NotificationPreferences preferences) {
        var profile = new UserProfile();
        profile.id = id;
        profile.userId = userId;
        profile.organizationId = organizationId;
        profile.name = name;
        profile.email = email;
        profile.area = area;
        profile.site = site;
        profile.language = language;
        profile.preferences = preferences;
        return profile;
    }

    public void updatePreferences(NotificationPreferences preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("organizations.error.profile.preferences.notNull");
        }
        this.preferences = preferences;
    }

    public void changeLanguage(String language) {
        if (language == null || !SUPPORTED_LANGUAGES.contains(language)) {
            throw new IllegalArgumentException("organizations.error.profile.language.invalid");
        }
        this.language = language;
    }
}
