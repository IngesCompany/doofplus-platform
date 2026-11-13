package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.RegisterOrganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Facility;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityType;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Organization aggregate root: a pharmaceutical laboratory identified by its RUC
 * that operates one or more facilities.
 */
@Getter
public class Organization extends AbstractDomainAggregateRoot<Organization> {
    private Long id;
    private String legalName;
    private Ruc ruc;
    private String region;
    private String ownerName;
    private OrganizationStatus status;
    private final List<Facility> facilities = new ArrayList<>();

    public Organization(RegisterOrganizationCommand command) {
        this.legalName = command.legalName();
        this.ruc = command.ruc();
        this.region = command.region();
        this.ownerName = command.ownerName();
        this.status = OrganizationStatus.PENDING_VERIFICATION;
    }

    private Organization() {
    }

    public static Organization reconstitute(Long id, String legalName, Ruc ruc, String region, String ownerName,
                                            OrganizationStatus status, List<Facility> facilities) {
        var organization = new Organization();
        organization.id = id;
        organization.legalName = legalName;
        organization.ruc = ruc;
        organization.region = region;
        organization.ownerName = ownerName;
        organization.status = status;
        if (facilities != null) organization.facilities.addAll(facilities);
        return organization;
    }

    public Facility addFacility(String name, FacilityType type, String timezone) {
        if (this.status == OrganizationStatus.INACTIVE) {
            throw new IllegalStateException("organizations.error.organization.inactive");
        }
        var facility = new Facility(name, type, timezone, this.ownerName);
        this.facilities.add(facility);
        return facility;
    }

    public void verify() {
        if (this.status == OrganizationStatus.ACTIVE) {
            throw new IllegalStateException("organizations.error.organization.alreadyActive");
        }
        if (this.status == OrganizationStatus.INACTIVE) {
            throw new IllegalStateException("organizations.error.organization.inactive");
        }
        this.status = OrganizationStatus.ACTIVE;
    }

    public void deactivate() {
        if (this.status == OrganizationStatus.INACTIVE) {
            throw new IllegalStateException("organizations.error.organization.alreadyInactive");
        }
        this.status = OrganizationStatus.INACTIVE;
    }

    public List<Facility> getFacilities() {
        return Collections.unmodifiableList(this.facilities);
    }
}
