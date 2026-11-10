package com.ingescompany.doofplus.platform.monitoring.application.queryservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsByEquipmentCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetSensorByCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetSensorByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Sensor} queries.
 */
public interface SensorQueryService {
    List<Sensor> handle(GetAllSensorsQuery query);
    List<Sensor> handle(GetAllSensorsByEquipmentCodeQuery query);
    Optional<Sensor> handle(GetSensorByIdQuery query);
    Optional<Sensor> handle(GetSensorByCodeQuery query);
}
