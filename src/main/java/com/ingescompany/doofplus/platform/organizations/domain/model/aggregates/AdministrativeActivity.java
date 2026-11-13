package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * AdministrativeActivity aggregate root: an entry of the recent activity shown in the administration overview.
 */
@Getter
public class AdministrativeActivity extends AbstractDomainAggregateRoot<AdministrativeActivity> {
    private Long id;
    private Long organizationId;
    private String title;
    private String actor;
    private LocalDateTime occurredAt;

    public AdministrativeActivity(Long organizationId, String title, String actor) {
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizations.error.organizationId.invalid");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("organizations.error.activity.title.notBlank");
        }
        this.organizationId = organizationId;
        this.title = title;
        this.actor = actor;
        this.occurredAt = LocalDateTime.now();
    }

    private AdministrativeActivity() {
    }

    public static AdministrativeActivity reconstitute(Long id, Long organizationId, String title, String actor,
                                                      LocalDateTime occurredAt) {
        var activity = new AdministrativeActivity();
        activity.id = id;
        activity.organizationId = organizationId;
        activity.title = title;
        activity.actor = actor;
        activity.occurredAt = occurredAt;
        return activity;
    }
}
