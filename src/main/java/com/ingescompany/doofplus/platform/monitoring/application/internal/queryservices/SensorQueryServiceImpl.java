package com.ingescompany.doofplus.platform.monitoring.application.internal.queryservices;

import com.ingescompany.doofplus.platform.monitoring.application.queryservices.SensorQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsByEquipmentCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetSensorByCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetSensorByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link SensorQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class SensorQueryServiceImpl implements SensorQueryService {

    private final SensorRepository sensorRepository;

    public SensorQueryServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public List<Sensor> handle(GetAllSensorsQuery query) {
        return sensorRepository.findAll();
    }

    @Override
    public List<Sensor> handle(GetAllSensorsByEquipmentCodeQuery query) {
        return sensorRepository.findAllByEquipmentCode(query.equipmentCode());
    }

    @Override
    public Optional<Sensor> handle(GetSensorByIdQuery query) {
        return sensorRepository.findById(query.id());
    }

    @Override
    public Optional<Sensor> handle(GetSensorByCodeQuery query) {
        return sensorRepository.findByCode(query.code());
    }
}
