package com.ingescompany.doofplus.platform.monitoring.application.queryservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetEquipmentByCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetEquipmentByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Equipment} queries.
 */
public interface EquipmentQueryService {
    List<Equipment> handle(GetAllEquipmentQuery query);
    Optional<Equipment> handle(GetEquipmentByIdQuery query);
    Optional<Equipment> handle(GetEquipmentByCodeQuery query);
}
