package com.ingescompany.doofplus.platform.organizations.application.internal.commandservices;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.OrganizationCommandService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.AddPlantCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.RegisterOrganizationCommand;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Result<Organization, ApplicationError> handle(RegisterOrganizationCommand command) {
        if (organizationRepository.existsByRuc(command.ruc())) {
            return Result.failure(ApplicationError.conflict("Organization",
                    "organizations.error.ruc.duplicate"));
        }
        try {
            var organization = new Organization(command);
            return Result.success(organizationRepository.save(organization));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("organization", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-organization", e.getMessage()));
        }
    }

    @Override
    public Result<Organization, ApplicationError> handle(AddPlantCommand command) {
        var organizationOptional = organizationRepository.findById(command.organizationId());
        if (organizationOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Organization", command.organizationId().toString()));
        }
        var organization = organizationOptional.get();
        try {
            organization.addPlant(command.name(), command.address());
            return Result.success(organizationRepository.save(organization));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("plant", e.getMessage()));
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("add-plant", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("add-plant", e.getMessage()));
        }
    }
}
