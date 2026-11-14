package com.ingescompany.doofplus.platform.organizations.application.internal.queryservices;

import com.ingescompany.doofplus.platform.organizations.application.queryservices.AdministrativeActivityQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.AdministrativeActivity;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllAdministrativeActivitiesQuery;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.AdministrativeActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrativeActivityQueryServiceImpl implements AdministrativeActivityQueryService {
    private final AdministrativeActivityRepository administrativeActivityRepository;

    public AdministrativeActivityQueryServiceImpl(AdministrativeActivityRepository administrativeActivityRepository) {
        this.administrativeActivityRepository = administrativeActivityRepository;
    }

    @Override
    public List<AdministrativeActivity> handle(GetAllAdministrativeActivitiesQuery query) {
        return administrativeActivityRepository.findAll();
    }
}
