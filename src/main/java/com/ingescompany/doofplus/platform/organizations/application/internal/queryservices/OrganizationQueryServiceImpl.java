package com.ingescompany.doofplus.platform.organizations.application.internal.queryservices;

import com.ingescompany.doofplus.platform.organizations.application.queryservices.OrganizationQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetOrganizationByRucQuery;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    private final OrganizationRepository organizationRepository;

    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> handle(GetOrganizationByIdQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    @Override
    public Optional<Organization> handle(GetOrganizationByRucQuery query) {
        return organizationRepository.findByRuc(query.ruc());
    }

    @Override
    public List<Organization> handle(GetAllOrganizationsQuery query) {
        return organizationRepository.findAll();
    }
}
