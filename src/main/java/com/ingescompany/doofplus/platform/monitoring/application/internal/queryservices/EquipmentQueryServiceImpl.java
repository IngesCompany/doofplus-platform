package com.ingescompany.doofplus.platform.monitoring.application.internal.queryservices;

import com.ingescompany.doofplus.platform.monitoring.application.queryservices.EquipmentQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetEquipmentByCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetEquipmentByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link EquipmentQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class EquipmentQueryServiceImpl implements EquipmentQueryService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentQueryServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Equipment> handle(GetAllEquipmentQuery query) {
        return equipmentRepository.findAll();
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentByIdQuery query) {
        return equipmentRepository.findById(query.id());
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentByCodeQuery query) {
        return equipmentRepository.findByCode(query.code());
    }
}
