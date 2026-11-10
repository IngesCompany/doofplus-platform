package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.organizations.domain.model.comands.RegisterOrgganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Plant;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
public class Organization {
    private Long id;
    private String legalName;
    private Ruc ruc;
    private OrganizationStatus status;
    private final List<Plant> plants = new ArrayList<>();

    public Organization(RegisterOrgganizationCommand command) {
        this.legalName = command.legalName();
        this.ruc = command.ruc();
        this.status = OrganizationStatus.ACTIVE;
        this.addPlant(command.plantName(), command.plantAddress());
    }

    private Organization() {
    }

    public static Organization reconstitute(Long id, String legalName, Ruc ruc, OrganizationStatus status, List<Plant> plants) {

        var organization = new Organization();
        organization.id = id;
        organization.legalName = legalName;
        organization.ruc = ruc;
        organization.status = status;
        if (plants != null) {
            organization.plants.addAll(plants);
        }
        return organization;
    }

    private Plant addPlant(String name, String address) {
        if (this.status == OrganizationStatus.SUSPENDED) {
            throw new IllegalStateException("organizations.error.organization.suspended");
        }
        var plant = new Plant(name, address);
        this.plants.add(plant);
        return plant;
    }

    public void suspend() {
        if (this.status == OrganizationStatus.SUSPENDED) {
            throw new IllegalStateException("organizations.error.organization.alreadySuspended");
        }
        this.status = OrganizationStatus.SUSPENDED;
    }

    public void reactivate() {
        if (this.status == OrganizationStatus.ACTIVE) {
            throw new IllegalStateException("organizations.error.organization.alreadyActive");
        }
        this.status = OrganizationStatus.ACTIVE;
    }

    public List<Plant> getPlants() {
        return Collections.unmodifiableList(this.plants);
    }

}