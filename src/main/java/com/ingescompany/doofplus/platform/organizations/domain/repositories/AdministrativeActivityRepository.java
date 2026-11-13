package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.AdministrativeActivity;

import java.util.List;

public interface AdministrativeActivityRepository {

    AdministrativeActivity save(AdministrativeActivity activity);

    List<AdministrativeActivity> findAll();
}
