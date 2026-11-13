package com.ingescompany.doofplus.platform.organizations.application.internal.commandservices;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.OrganizationCommandService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.AdministrativeActivity;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.AddFacilityCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.RegisterOrganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.entities.Facility;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.AdministrativeActivityRepository;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;
    private final AdministrativeActivityRepository administrativeActivityRepository;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository,
                                          AdministrativeActivityRepository administrativeActivityRepository) {
        this.organizationRepository = organizationRepository;
        this.administrativeActivityRepository = administrativeActivityRepository;
    }

    @Override
    public Result<Organization, ApplicationError> handle(RegisterOrganizationCommand command) {
        if (organizationRepository.existsByRuc(command.ruc())) {
            return Result.failure(ApplicationError.conflict("Organization",
                    "organizations.error.ruc.duplicate"));
        }
        try {
            var organization = organizationRepository.save(new Organization(command));
            administrativeActivityRepository.save(new AdministrativeActivity(
                    organization.getId(), "Organization registered", organization.getOwnerName()));
            return Result.success(organization);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("organization", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-organization", e.getMessage()));
        }
    }

    @Override
    public Result<Facility, ApplicationError> handle(AddFacilityCommand command) {
        var organizationOptional = organizationRepository.findById(command.organizationId());
        if (organizationOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Organization", command.organizationId().toString()));
        }
        var organization = organizationOptional.get();
        try {
            organization.addFacility(command.name(), command.type(), command.timezone());
            var facilities = organizationRepository.save(organization).getFacilities();
            // The saved aggregate carries the generated id of the facility that was just added: the last one.
            var facility = facilities.get(facilities.size() - 1);
            administrativeActivityRepository.save(new AdministrativeActivity(
                    organization.getId(), "Facility %s added".formatted(facility.getName()),
                    organization.getOwnerName()));
            return Result.success(facility);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("facility", e.getMessage()));
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("add-facility", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("add-facility", e.getMessage()));
        }
    }
}
